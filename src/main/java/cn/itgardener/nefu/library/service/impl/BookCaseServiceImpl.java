/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.impl;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.Page;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.PageUtil;
import cn.itgardener.nefu.library.common.util.VerifyUtil;
import cn.itgardener.nefu.library.core.mapper.BookCaseMapper;
import cn.itgardener.nefu.library.core.mapper.ConfigMapper;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.BookCase;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.LocationVo;
import cn.itgardener.nefu.library.core.model.vo.ShipVo;
import cn.itgardener.nefu.library.service.BookCaseService;
import cn.itgardener.nefu.library.service.ReservationService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.itgardener.nefu.library.common.GlobalConst.USER_DISABLE;

/**
 * @author : chenchenT CMY
 * @date : 2018/10/28
 * @since : Java 8
 */
@Service
public class BookCaseServiceImpl implements BookCaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookCaseMapper bookCaseMapper;

    private final UserMapper userMapper;

    private final RedisDao redisDao;

    private final ConfigMapper configMapper;

    private final ReservationService reservationService;

    @Autowired
    public BookCaseServiceImpl(BookCaseMapper bookCaseMapper, ConfigMapper configMapper, UserMapper userMapper, RedisDao redisDao, ReservationService reservationService) {
        this.bookCaseMapper = bookCaseMapper;
        this.userMapper = userMapper;
        this.configMapper = configMapper;
        this.redisDao = redisDao;
        this.reservationService = reservationService;
    }

    @Override
    public Map<String, Object> getLocationByUserId(User user) throws LibException {

        Map<String, Object> rtv = null;
        List<User> u = userMapper.selectByCondition(user);
        User user1 = u.get(0);

        if (null != user1) {

            if (USER_DISABLE == user1.getType()) {
                throw new LibException("当前用户已被禁用!");
            } else {
                BookCase bookCase = bookCaseMapper.selectByUserId(user1);
                if (null != bookCase) {
                    rtv = new HashMap<>(2);
                    rtv.put("location", bookCase.getLocation());
                    rtv.put("number", bookCase.getSystemId());
                } else {
                    throw new LibException("您没有可用柜子!");
                }
            }
        }
        return rtv;
    }

    @Override
    public boolean putShip(ShipVo shipVO) throws LibException {
        if (null == shipVO.getStudentId()) {
            shipVO.setStatus(0);
        } else {
            shipVO.setStatus(1);
            User user = bookCaseMapper.selectUserIdByStudentId(shipVO);
            BookCase bookCase = bookCaseMapper.selectBySystemId(shipVO);
            if (null == bookCase) {
                throw new LibException("此书包柜不存在");
            }
            if (null == user) {
                throw new LibException("此学号不存在");
            }

            shipVO.setUserId(user.getSystemId());

        }
        int i = bookCaseMapper.updateSingleShip(shipVO);
        if (0 == i) {
            throw new LibException("修改失败");
        } else {
            redisDao.removeAllKey();
            redisDao.updateRedis(); // 同步MySQL数据库
            return true;
        }

    }

    @Override
    public RestData setKeepById(BookCaseVo bookCaseVo) {
        int success = 0;
        List<Integer> data = bookCaseVo.getArray();
        if (0 == data.size() || null == data) {
            return new RestData(1, "无效参数");
        } else {
            for (int count = 0; count < data.size(); count++) {
                BookCaseVo vo = new BookCaseVo();
                vo.setSystemId(data.get(count));
                List<BookCase> bookCases = bookCaseMapper.selectBookCaseByCondition(vo);
                if (null != bookCases && 1 == bookCases.size()) {
                    if (null != bookCases.get(0).getUserId()) {
                        return new RestData(1, "编号为" + bookCases.get(0).getNumber() + "的柜子存在已被占用的情况!");
                    }
                } else {
                    return new RestData(1, "柜子编号有误!");
                }
            }

            for (int count = 0; count < data.size(); count++) {
                BookCaseVo vo = new BookCaseVo();
                vo.setSystemId(data.get(count));
                if (0 >= bookCaseMapper.setBookCaseByCondition(vo)) {
                    success = 1;
                    break;
                }
            }
            if (0 == success) {
                redisDao.removeAllKey();
                redisDao.updateRedis();
                return new RestData(0, "操作成功!");
            } else {
                return new RestData(1, "操作失败,请重试!");
            }
        }
    }

    @Override
    public RestData setKeepByNumber(BookCaseVo bookCaseVo) {
        if (!VerifyUtil.verifyArea(bookCaseVo)) {
            return new RestData(1, "此区域不存在!");
        }

        if (null == bookCaseVo.getFloor() || null == bookCaseVo.getArea() || null == bookCaseVo.getStart() || null == bookCaseVo.getEnd()) {
            return new RestData(1, "无效参数!");
        } else {

            if (bookCaseVo.getEnd() >= bookCaseVo.getStart() && 0 < bookCaseVo.getStart()) {

                for (int count = bookCaseVo.getStart(); count <= bookCaseVo.getEnd(); count++) {
                    BookCaseVo vo = new BookCaseVo();
                    vo.setNumber(count);
                    vo.setLocation(bookCaseVo.getFloor() + "_" + bookCaseVo.getArea());
                    List<BookCase> bookCases = bookCaseMapper.selectBookCaseByCondition(vo);
                    if (null != bookCases && 1 == bookCases.size()) {
                        if (null != bookCases.get(0).getUserId()) {
                            return new RestData(1, "编号为" + bookCases.get(0).getNumber() + "的柜子存在已被占用的情况!");
                        }
                    } else {
                        return new RestData(1, "柜子编号有误!");
                    }
                }

                bookCaseVo.setLocation(bookCaseVo.getFloor() + "_" + bookCaseVo.getArea());
                int i = bookCaseMapper.setBookCaseByCondition(bookCaseVo);
                if (0 == i) {
                    return new RestData(1, "未知错误!");
                } else {
                    redisDao.removeAllKey();
                    redisDao.updateRedis();
                    return new RestData(true);
                }
            } else {
                return new RestData(1, "无效参数!");
            }
        }

    }


    @Override
    public RestData deleteShip(List<Integer> data) {
        int success = 0;
        if (null == data || 0 == data.size()) {
            if (0 >= bookCaseMapper.deleteAllShip()) {
                return new RestData(1, "柜子已全部清空关系!");
            } else {
                redisDao.removeAllKey();
                redisDao.updateRedis();
                return new RestData(0, "操作成功!");
            }
        } else {
            for (Integer datum : data) {
                BookCase bookCase = new BookCase();
                bookCase.setSystemId(datum);
                if (0 >= bookCaseMapper.deleteShipById(bookCase)) {
                    success = 1;
                    break;
                }
            }
            if (0 == success) {
                redisDao.removeAllKey();
                redisDao.updateRedis();
                return new RestData(0, "操作成功!");
            } else {
                return new RestData(1, "操作失败,请重试!");
            }
        }
    }

    @Override
    public List<Map<String, Object>> getBagNum(String floor) {
        List<Map<String, Object>> rtv = new ArrayList<>();
        for (int i = 1; i <= Integer.parseInt(redisDao.get("floor_" + floor)); i++) {
            Map<String, Object> map = new HashMap<>(2);
            int num = Integer.parseInt(redisDao.get("location_" + floor + "_" + i));
            map.put("location", floor + "_" + i);
            map.put("num", num);
            rtv.add(map);
        }
        return rtv;
    }

    @Override
    public String processParameter(BookCaseVo bookCaseVo) {

        /*对书包柜编号范围Id分割*/
        if (null != bookCaseVo.getId() && 0 != bookCaseVo.getId().length()) {
            String id = bookCaseVo.getId();
            if (id.indexOf("-") != -1) {
                String[] splitstr = id.split("-", -1);
                if (null == splitstr[0] || "".equals(splitstr[0])) {
                    bookCaseVo.setL(splitstr[1]);
                    bookCaseVo.setR(splitstr[1]);
                } else if (null == splitstr[1] || "".equals(splitstr[1])) {
                    bookCaseVo.setL(splitstr[0]);
                    bookCaseVo.setR(splitstr[0]);
                } else {
                    if (Integer.parseInt(splitstr[1]) < Integer.parseInt(splitstr[0])) {
                        bookCaseVo.setL(splitstr[1]);
                        bookCaseVo.setR(splitstr[0]);
                    } else {
                        bookCaseVo.setL(splitstr[0]);
                        bookCaseVo.setR(splitstr[1]);
                    }
                }
            } else {
                bookCaseVo.setL(id);
                bookCaseVo.setR(id);
            }
        }

        /*根据学号查找与书包柜关联的userId*/
        String mes = null;
        if (null != bookCaseVo.getStudentId() && 0 != bookCaseVo.getStudentId().length()) {
            User user = new User();
            user.setStudentId(bookCaseVo.getStudentId());
            List<User> users = userMapper.selectByCondition(user);
            if (0 < users.size()) {
                User u = users.get(0);
                bookCaseVo.setUserId(u.getSystemId());
            } else {
                mes = "请确认学号是否有误";
            }
        }
        return mes;
    }

    private RestData encapsulate(List<BookCase> bookCases, Page page) {

        List<Map<String, Object>> rtv = new ArrayList<>();
        for (BookCase data : bookCases) {
            Map<String, Object> map = new HashMap<>(4);
            if (null != data.getSystemId()) {
                map.put("systemId", data.getSystemId());
            } else {
                map.put("systemId", "");
            }
            if (null != data.getLocation()) {
                map.put("location", data.getLocation());
            } else {
                map.put("location", "");
            }
            if (null != data.getLocation()) {
                map.put("location", data.getLocation());
            } else {
                map.put("location", "");
            }
            if (null != data.getNumber()) {
                map.put("number", data.getNumber());
            } else {
                map.put("number", "");
            }
            if (null != data.getStatus()) {
                map.put("status", data.getStatus());
            } else {
                map.put("status", "");
            }
            Integer userId = data.getUserId();
            User user = userMapper.selectByUserId(data);
            if (null != userId && null != userMapper.selectByUserId(data)) {
                map.put("studentId", user.getStudentId());
                map.put("studentName", user.getStudentName());
            } else {
                map.put("studentId", "");
                map.put("studentName", "");
            }
            rtv.add(map);
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("ships", rtv);
        map.put("pages", page);
        return new RestData(map);
    }

    @Override
    public RestData selectDetailByCondition(BookCaseVo bookCaseVo) {

        if (null != bookCaseVo.getStatus() && (2 == bookCaseVo.getStatus() || 0 == bookCaseVo.getStatus()) && null != bookCaseVo.getStudentId()) {
            return new RestData(1, "无效查询");
        }

        String message = processParameter(bookCaseVo);
        /*传入的学号有误*/
        if (null != message) {
            return new RestData(1, message);
        }

        Page page;
        if (null == bookCaseVo.getPage()) {
            bookCaseVo.setPage(1);
        }
        if (null != bookCaseVo.getSystemIdRight() && null != bookCaseVo.getSystemIdLeft()) {
            if (bookCaseVo.getSystemIdRight() < bookCaseVo.getSystemIdRight()) {
                return new RestData(1, "输入范围有误");
            }
        }
        page = bookCaseMapper.countByCondition(bookCaseVo);
        page.setNowPage(bookCaseVo.getPage());
        page = PageUtil.checkPage(page);

        List<BookCase> bookCases = bookCaseMapper.selectDetailByCondition(bookCaseVo, page);
        return encapsulate(bookCases, page);

    }

    /**
     * 选择出最大数量的区域
     *
     * @return maxlocation 区域
     */
    private String maxLocation() {
        int max = 0;
        int nowValue;
        String maxLocation = null;
        for (int i = 1; i <= 6; i++) {
            int locationNum = Integer.parseInt(redisDao.get("floor_" + i));
            for (int j = 0; j < locationNum; j++) {
                try {
                    // redis中可能没有该区域
                    nowValue = Integer.parseInt(redisDao.get("location_" + i + "_" + j));
                } catch (Exception e) {
                    nowValue = 0;
                }
                if (nowValue > max) {
                    max = nowValue;
                    maxLocation = i + "_" + j;
                }
            }
        }
        return maxLocation;
    }

    @Override
    public boolean postBoxOrder(BookCaseVo bookCaseVo) throws LibException {

        reservationService.verifyCode(bookCaseVo.getVerifyCode(), bookCaseVo.getStudentId());

        String key = bookCaseVo.getStudentId();
        String location;

        if (bookCaseVo.getLocation() != null) {
            location = bookCaseVo.getLocation();
        } else {
            location = maxLocation();
        }

        long count = redisDao.getListSize("userQueue") + Integer.parseInt(redisDao.get("popCount"));
        redisDao.set("l_" + key, location + "," + count);
        int total = Integer.parseInt(redisDao.get("total"));

        /*
          1. 进来先查 location 是否还有柜子
          2. 如果有,判断 c_ 大于一 入队 不大于一 total--
          3. 如果没有柜子返回false
         */
        if (total > 0 && Integer.parseInt(redisDao.get("location_" + location)) > 0) {
            if (redisDao.inc("c_" + key, 1) > 1) {
                redisDao.pushValue("userQueue", key);
            } else {
                redisDao.pushValue("userQueue", key);
                redisDao.dec("total", 1);
            }
        } else {
            throw new LibException("排队失败,或已经没有可用柜子,请点击页面刷新状态按钮获取最新数据.");
        }
        return true;
    }

    @Override
    public void boxQueue(String studentId) {

        // 当前是否是第一次排队,如果不是不处理
        if (redisDao.dec("c_" + studentId, 1) == 0) {
            logger.info("====当前处理{}", studentId);
            //l为该学生请求的区域
            String l = redisDao.get("l_" + studentId).split(",")[0];
            // 如果当前区域没有柜子
            if ("0".equals(redisDao.get("location_" + l))) {
                // 取当前区域中的最大值
                l = maxLocation();
            }
            // 不包含在finish中就分配柜子
            if (!redisDao.isMember("finish", studentId)) {
                BookCase bookCase = bookCaseMapper.selectBookCaseNumberByLocation(l);
                User user = new User();
                user.setStudentId(studentId);
                List<User> reUser = userMapper.selectByCondition(user);
                bookCaseMapper.updateOwnerByBcId(bookCase.getSystemId(), reUser.get(0).getSystemId());
                redisDao.dec("location_" + l, 1);
                redisDao.add("finish", studentId);
            } else {
                logger.info(studentId + "已经分配柜子,无需再分配");
            }
        }
    }


    @Override
    public RestData deleteBookcaseById(BookCaseVo bookCaseVo) throws LibException {
        if (0 != bookCaseVo.getArray().size()) {
            for (Integer systemId : bookCaseVo.getArray()) {
                bookCaseVo.setSystemId(systemId);
                bookCaseMapper.deleteBookcaseById(bookCaseVo);
            }
            redisDao.removeAllKey();
            redisDao.updateRedis();
            return new RestData(true);
        } else {
            throw new LibException("数组为空");
        }
    }

    @Override
    public RestData deleteBookcaseByNumber(BookCaseVo bookCaseVo) throws LibException {
        if (!VerifyUtil.verifyArea(bookCaseVo)) {
            throw new LibException("此区域不存在");
        }
        bookCaseVo.setLocation(bookCaseVo.getFloor() + "_" + bookCaseVo.getArea());
        if (bookCaseVo.getEnd() >= bookCaseVo.getStart() && 0 < bookCaseVo.getStart()) {
            int i = bookCaseMapper.deleteBookcaseByRange(bookCaseVo);
            if (0 == i) {
                throw new LibException("输入的数据有误");
            } else {
                redisDao.removeAllKey();
                redisDao.updateRedis();
                return new RestData(true);
            }
        } else {
            throw new LibException("输入的数据有误");
        }
    }

    @Override
    public String popQueue() {
        String studentId = redisDao.popValue("userQueue");
        if (studentId != null) {
            redisDao.inc("popCount", 1);
        }
        return studentId;
    }

    @Override
    public boolean addBookcase(BookCaseVo bookCaseVo) {
        List<Integer> list;
        List<Config> list1;
        int number = 0;
        list1 = configMapper.selectLocation(bookCaseVo.getFloor() + "_" + bookCaseVo.getArea());
        if (list1.size() == 0) {
            logger.info("所选区域不存在");
            return false;
        }
        list = bookCaseMapper.getMaxNumber(bookCaseVo.getFloor() + "_" + bookCaseVo.getArea());
        if (null != list.get(0)) {
            number = list.get(0);
        }
        for (int i = 0; i < bookCaseVo.getTotal(); i++) {
            if (0 == bookCaseMapper.addBookcase(bookCaseVo.getFloor() + "_" + bookCaseVo.getArea(), ++number)) {
                return false;
            }
        }
        redisDao.removeAllKey();
        redisDao.updateRedis();
        return true;
    }

    @Override
    public boolean addLocation(LocationVo locationVo) {
        List<Config> list;
        int maxarea = 0;
        list = configMapper.selectFloorLocation(locationVo);
        for (Config config : list) {
            String[] x = config.getConfigKey().split("_");
            if (Integer.parseInt(x[1]) > maxarea) {
                maxarea = Integer.parseInt(x[1]);
            }
        }
        maxarea++;
        locationVo.setLocation(locationVo.getFloor() + "_" + maxarea);
        configMapper.addLocation(locationVo);
        redisDao.removeAllKey();
        redisDao.updateRedis();
        return true;
    }

    @Override
    public RestData deleteLocation(String location) throws LibException {
        int i = configMapper.deleteLocation(location);
        int i1 = bookCaseMapper.deleteBookcaseByLocation(location);
        if (i > 0 & i1 > 0) {
            return new RestData(true);
        } else {
            throw new LibException("删除区域失败");
        }
    }

    @Override
    public RestData postBookcase(BookCaseVo bookCaseVo) {
        if (null != bookCaseVo.getSystemIdRight() && null != bookCaseVo.getSystemIdLeft()) {
            if (bookCaseVo.getSystemIdRight() < bookCaseVo.getSystemIdLeft()) {
                return new RestData(1, "输入数据异常，请重新输入");
            } else {
                if (bookCaseMapper.updateBookcaseByRange(bookCaseVo) > 0) {
                    return new RestData(true);
                } else {
                    return new RestData(1, "预留失败");
                }
            }
        } else {
            return new RestData(1, "输入数据异常，请重新输入");
        }

    }

    @Override
    public RestData deleteBookcase(BookCaseVo bookCaseVo) {
        if (null != bookCaseVo.getSystemIdRight() && null != bookCaseVo.getSystemIdLeft()) {
            if (bookCaseVo.getSystemIdRight() < bookCaseVo.getSystemIdLeft()) {
                return new RestData(1, "输入数据异常，请重新输入");
            } else {
                if (bookCaseMapper.deleteBookcaseByIdRange(bookCaseVo) > 0) {
                    return new RestData(true);
                } else {
                    return new RestData(1, "删除失败");
                }
            }
        } else {
            return new RestData(1, "输入数据异常，请重新输入");
        }
    }

    @Override
    public void downMessage(HttpServletResponse response) {
        List<BookCase> bookCases = bookCaseMapper.selectBookcaseNoCondition();
        List<String> tableTitles = new LinkedList<>();
        tableTitles.add("书包柜编号");
        tableTitles.add("区域");
        tableTitles.add("状态");
        tableTitles.add("使用者学号");
        tableTitles.add("使用者姓名");

        //总列数
        int cellNum = tableTitles.size();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        // 设置文件MIME类型
        response.setContentType("application/ms-excel");
        // 设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + format + ".xlsx");

        // 创建excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表sheet
        XSSFSheet sheet = workbook.createSheet("预约信息表");
        // 垂直居中
        sheet.setVerticallyCenter(true);
        // 水平居中
        sheet.setHorizontallyCenter(true);
        //页边距
        sheet.setMargin(Sheet.TopMargin, 0.5D);
        sheet.setMargin(Sheet.BottomMargin, 0.5D);
        sheet.setMargin(Sheet.LeftMargin, 0.25D);
        sheet.setMargin(Sheet.RightMargin, 0.25D);
        //自适应列宽
        sheet.autoSizeColumn(1, true);
        //设置默认宽度
        sheet.setDefaultColumnWidth(15);
        // 创建行
        XSSFRow row;
        // 创建列
        XSSFCell cell;
        // 插入第一行数据的表头
        row = sheet.createRow(0);

        for (int i = 0; i < cellNum; i++) {
            XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
            // 水平居中
            xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 垂直居中
            xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // 上下左右边框
            xssfCellStyle.setBorderTop(BorderStyle.THIN);
            xssfCellStyle.setBorderBottom(BorderStyle.THIN);
            xssfCellStyle.setBorderLeft(BorderStyle.THIN);
            xssfCellStyle.setBorderRight(BorderStyle.THIN);
            //创建单元格
            cell = row.createCell(i);
            cell.setCellStyle(xssfCellStyle);
            cell.setCellValue(tableTitles.get(i));
        }
        int rowIndex = 1;
        int cellIndex;

        for (BookCase bookCase : bookCases) {
            row = sheet.createRow(rowIndex);
            rowIndex++;
            cellIndex = 0;
            XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
            // 水平居中
            xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 垂直居中
            xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // 上写左右边框
            xssfCellStyle.setBorderTop(BorderStyle.THIN);
            xssfCellStyle.setBorderBottom(BorderStyle.THIN);
            xssfCellStyle.setBorderLeft(BorderStyle.THIN);
            xssfCellStyle.setBorderRight(BorderStyle.THIN);
            //编号
            if (null != bookCase.getSystemId()) {
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue(bookCase.getSystemId());
            }
            //区域
            if (null != bookCase.getLocation()) {
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(xssfCellStyle);
                String[] split = bookCase.getLocation().split("_");
                cell.setCellValue(split[0] + "层" + split[1] + "列");
            }
            //状态
            if (null != bookCase.getStatus()) {
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(xssfCellStyle);
                if (0 == bookCase.getStatus()) {
                    cell.setCellValue("空闲");
                }
                if (1 == bookCase.getStatus()) {
                    cell.setCellValue("被占用");
                }
                if (2 == bookCase.getStatus()) {
                    cell.setCellValue("被预留");
                }
            }
            //学生的学号
            if (null != bookCase.getUserId()) {
                User user = new User();
                user.setSystemId(bookCase.getUserId());
                List<User> users = userMapper.selectByCondition(user);
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue(users.get(0).getStudentId());
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue(users.get(0).getStudentName());

            } else {
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue("");
                cell = row.createCell(cellIndex++);
                cell.setCellStyle(xssfCellStyle);
                cell.setCellValue("");
            }
        }
        // 创建excel文件
        // 将excel写入
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

