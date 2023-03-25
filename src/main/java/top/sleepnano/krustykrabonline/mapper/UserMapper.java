package top.sleepnano.krustykrabonline.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import top.sleepnano.krustykrabonline.entity.User;

import java.util.List;

public interface UserMapper {

    @Select("Select id,user_name,user_pass,user_addr,user_phone,user_no,create_time,modify_time " +
            "from user_list where user_name = #{username}")
    User selectUserByUserName(String username);

    @Select("SELECT" +
            "    DISTINCT m.`perms`" +
            "FROM" +
            "    sys_user_role ur" +
            "        LEFT JOIN `sys_role` r ON ur.`role_id` = r.`id`" +
            "        LEFT JOIN `sys_role_menu` rm ON ur.`role_id` = rm.`role_id`" +
            "        LEFT JOIN `sys_menu` m ON m.`id` = rm.`menu_id`" +
            "WHERE" +
            "        user_id = #{userNo}" +
            "  AND r.`status` = 0" +
            "  AND m.`status` = 0")
    List<String> getUserPermission(String userNo);

    @Insert("Insert into user_list (user_name,user_pass,user_phone) Value (#{username},#{userpass},#{userphone})")
    Integer insertUser(String username,String userpass,String userphone);
}
