package cn.edu.nefu.library.service.impl;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.mapper.BookCaseMapper;
import cn.edu.nefu.library.core.mapper.UserMapper;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
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

    public BookCaseImpl(BookCaseMapper bookCaseMapper,  UserMapper userMapper){
        this.bookCaseMapper=bookCaseMapper;
        this.userMapper=userMapper;
    }

    @Override
    public Map<String, Object> getLocationByUserId(User user) throws LibException {

        Map<String, Object> rtv = null;
        User user1= userMapper.selectByStudenId(user);

        if (null != user1 ) {

            if (2 == user1.getType()) {
                throw new LibException("当前用户已被禁用!");
            }
            else {
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
    public List<Map<String,Object>> getBagNum() throws  LibException{
        List<Map<String,Object>> rtv = new ArrayList<>();
        List<BookCase> result = bookCaseMapper.selectBagNum();
        if(result!=null){
            for(BookCase bookCase:result){
                Map<String,Object> map = new HashMap<>();
                map.put("location",bookCase.getLocation());
                map.put("num",bookCase.getNumber());
                rtv.add(map);
            }
        }else{
            throw new LibException("没有柜子的信息");
        }
        return rtv;
    }

}

