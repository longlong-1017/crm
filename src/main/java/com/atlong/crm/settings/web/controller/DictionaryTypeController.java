package com.atlong.crm.settings.web.controller;

import com.atlong.crm.commons.constant.Constant;
import com.atlong.crm.commons.domain.ReturnObject;
import com.atlong.crm.settings.domian.DictionaryType;
import com.atlong.crm.settings.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/10 17:20
 */
@Controller
public class DictionaryTypeController {
    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @RequestMapping("/settings/dictionary/type/index.do")
    public String index(HttpServletRequest request) {
        List<DictionaryType> typeList = dictionaryTypeService.queryAll();
        request.setAttribute("typeList", typeList);
        return "settings/dictionary/type/index";
    }

    @RequestMapping("/settings/dictionary/type/save.do")
    public String save() {
        return "settings/dictionary/type/save";
    }

    @RequestMapping("/settings/dictionary/type/checkCodeIsExist.do")
    public @ResponseBody Object checkCodeIsExist(String code) {
        int count = dictionaryTypeService.queryCountByCode(code);
        ReturnObject returnObject = new ReturnObject();
        if (count != 0) {
            returnObject.setMessage("编码已存在，请重新输入");
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
        } else {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/saveDicType.do")
    public @ResponseBody Object saveDicType(DictionaryType dictionaryType) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = dictionaryTypeService.saveDictionaryType(dictionaryType);
            if (ret == 1) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后尝试");
            }
        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后尝试");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/deleteDicType.do")
    public @ResponseBody Object deleteDicType(String[] code) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = dictionaryTypeService.deleteDictionaryTypeByCodes(code);
            if (ret > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
            e.printStackTrace();
        }
        return returnObject;
    }

    @RequestMapping("/settings/dictionary/type/edit.do")
    public String edit(DictionaryType dictionaryType,HttpServletRequest request){
        request.setAttribute("dictionaryType",dictionaryType);
        return "settings/dictionary/type/edit";
    }

    @RequestMapping("/settings/dictionary/type/updateDicType.do")
    public @ResponseBody Object updateDicType(DictionaryType dictionaryType){
        ReturnObject returnObject=new ReturnObject();
        try {
            int ret = dictionaryTypeService.editDictionaryByCode(dictionaryType);
            if (ret==1){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试");
            e.printStackTrace();
        }
        return returnObject;
    }
}
