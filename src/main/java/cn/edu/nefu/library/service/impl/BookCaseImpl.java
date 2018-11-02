package cn.edu.nefu.library.service.impl;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.Page;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.PageUtil;
import cn.edu.nefu.library.core.mapper.BookCaseMapper;
import cn.edu.nefu.library.core.mapper.UserMapper;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.BookCaseVo;
import cn.edu.nefu.library.service.BookCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : chenchenT CMY
 * @date : 2018/10/28
 * @since : Java 8
 */
@Service
public class BookCaseImpl implements BookCaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookCaseMapper bookCaseMapper;

    private final UserMapper userMapper;

    public BookCaseImpl(BookCaseMapper bookCaseMapper, UserMapper userMapper) {
        this.bookCaseMapper = bookCaseMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Map<String, Object> getLocationByUserId(User user) throws LibException {

        Map<String, Object> rtv = null;
        List<User> u = userMapper.selectByCondition(user);
        User user1 = u.get(0);

        if (null != user1) {

            if (2 == user1.getType()) {
                throw new LibException("当前用户已被禁用!");
            } else {
                BookCase bookCase = bookCaseMapper.selectByUserId(user1);
                if (null != bookCase) {
                    rtv = new HashMap<>(2);
                    rtv.put("location", bookCase.getLocation());
                    rtv.put("number", bookCase.getNumber());
                } else {
                    throw new LibException("您没有可用柜子!");
                }
            }
        }
        return rtv;
    }


    @Override
    public int setKeepByNumber(BookCase bookCase) {
        int success = 0;
        success = bookCaseMapper.setByNumber(bookCase);
        if (0 < success) {
            return 1;
        } else {
            return 0;

        }
    }

    @Override
    public int updateShipByNumber(BookCase bookCase) {
        int success = 0;
        success = bookCaseMapper.updateShipByNumber(bookCase);
        if (0 < success) {
            return 1;
        } else {
            return 0;

        }
    }


    @Override
    public List<Map<String, Object>> getBagNum() throws LibException {
        List<Map<String, Object>> rtv = new ArrayList<>();
        BookCase bookCase = new BookCase();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<>(2);
            bookCase.setLocation(i + 1);
            int num = bookCaseMapper.selectBagNum(bookCase);
            map.put("location", i + 1);
            map.put("num", num);
            rtv.add(map);
        }
        return rtv;
    }

    @Override
    public String ProcessParameter(BookCaseVo bookCaseVo) {

        if (null != bookCaseVo.getId() && 0 != bookCaseVo.getId().length()) {
            String id = bookCaseVo.getId();
            if (id.indexOf("-") != -1) {
                String[] splitstr = id.split("-");
                bookCaseVo.setL(splitstr[0]);
                bookCaseVo.setR(splitstr[1]);
            } else {
                bookCaseVo.setL(id);
                bookCaseVo.setR(id);
            }
        }

        String mes=null;
        if (null != bookCaseVo.getStudentId() && 0 != bookCaseVo.getStudentId().length()) {
            User user = new User();
            user.setStudentId(bookCaseVo.getStudentId());
            List<User> users = userMapper.selectByCondition(user);
            User u = users.get(0);
            if (null != u) {
                bookCaseVo.setUserId(u.getSystemId());
            }
            else {
                mes = "请确认学号是否有误";
                return mes;
            }
        }
        return mes;
    }

    @Override
    public RestData encapsulate(List<BookCase> bookCases, BookCaseVo bookCaseVo, Page page) {

        List<Map<String, Object>> rtv = new ArrayList<>();
        for (BookCase data : bookCases) {
            Map<String, Object> map = new HashMap<>(4);
            map.put("location", data.getLocation());
            map.put("id", data.getNumber());
            map.put("status", data.getStatus());
            Integer userId = data.getUserId();
            if (null != bookCaseVo.getStudentId()) {
                map.put("studentId", userMapper.selectByUserId(data).getStudentId());
            } else {
                map.put("studentId", "");
            }
            rtv.add(map);
        }
        return new RestData(rtv, page);
    }

    @Override
    public RestData selectDetailByCondition(BookCaseVo bookCaseVo) {

        String message=ProcessParameter(bookCaseVo);
        if(null!=message){
            return new RestData(1, message);
        }

        Page page;
        if (null == bookCaseVo.getPage()) {
            bookCaseVo.setPage(1);
        }
        if (null == bookCaseVo.getSystemId()) {
            page = bookCaseMapper.countByCondition(bookCaseVo);
            page.setNowPage(bookCaseVo.getPage());
            page = PageUtil.checkPage(page);
            List<BookCase> bookCases = bookCaseMapper.selectDetailByCondition(bookCaseVo, page);
            return encapsulate(bookCases, bookCaseVo, page);
        }

        page = bookCaseMapper.countByCondition(bookCaseVo);
        page.setNowPage(bookCaseVo.getPage());
        page = PageUtil.checkPage(page);
        List<BookCase> bookCases = bookCaseMapper.selectDetailByCondition(bookCaseVo, page);
        return encapsulate(bookCases, bookCaseVo, page);

    }
}



