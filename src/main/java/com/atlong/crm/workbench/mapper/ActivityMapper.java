package com.atlong.crm.workbench.mapper;

import com.atlong.crm.workbench.domian.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Jul 29 16:36:52 CST 2022
     */
    int deleteByPrimaryKey(String id);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Jul 29 16:36:52 CST 2022
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Jul 29 16:36:52 CST 2022
     */
    int updateByPrimaryKey(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Fri Jul 29 16:36:52 CST 2022
     * 添加市场活动
     */
    int insertActivity(Activity record);

    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);
    /**
     * 查询所有符合条件的活动条数
    * */
    int selectCountOfActivityByCondition(Map<String,Object> map);

    /**
     * 根据ID删除市场活动
    * */
    int deleteActivityByIds(String[] ids);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *根据ID查寻市场活动
     * @mbggenerated Fri Jul 29 16:36:52 CST 2022
     */
    Activity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *根据ID更新市场活动
     * @mbggenerated Fri Jul 29 16:36:52 CST 2022
     */
    int updateByPrimaryKeySelective(Activity record);

    //查询所有市场活动
    List<Activity> selectAllActivities();

    //查询市场活动通过ids
    List<Activity> selectActivitiesByIds(String[] ids);

    /**
     * 批量保存创建的市场活动
     * @param activities
     * @return
     */
    int insertActivities(List<Activity> activities);

    Activity selectActivityForDetailById(String id);

    List<Activity> selectActivityForDetailByClueId(String id);

    List<Activity> selectActivityForDetailByNameClueId(Map<String,Object> map);

    List<Activity> selectActivitiesForBoundByIds(String[] activityIds);

    List<Activity> selectActivityForConvertByNameClueId(Map<String, Object> map);
}