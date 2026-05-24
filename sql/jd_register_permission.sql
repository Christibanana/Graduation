-- 司法鉴定注册角色与菜单权限初始化脚本
-- 作用：
-- 1. 开启账号自助注册
-- 2. 创建“司法鉴定人”“司法鉴定机构”两个注册角色
-- 3. 两个角色仅授予“司法鉴定管理”菜单及其子菜单/按钮权限

set names utf8mb4;

update sys_config
set config_value = 'true', update_by = 'admin', update_time = sysdate()
where config_key = 'sys.account.registerUser';

insert into sys_role
    (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
values
    (101, '司法鉴定人', 'jd_appraiser', 3, '1', 1, 1, '0', '0', 'admin', sysdate(), '注册用户：鉴定人，仅可访问司法鉴定管理'),
    (102, '司法鉴定机构', 'jd_institution', 4, '1', 1, 1, '0', '0', 'admin', sysdate(), '注册用户：机构，仅可访问司法鉴定管理')
on duplicate key update
    role_name = values(role_name),
    role_key = values(role_key),
    status = '0',
    del_flag = '0',
    update_by = 'admin',
    update_time = sysdate(),
    remark = values(remark);

delete from sys_role_menu where role_id in (101, 102);

insert into sys_role_menu (role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r
join sys_menu m
where r.role_id in (101, 102)
  and (
    m.menu_id = 2000
    or m.parent_id = 2000
    or m.parent_id in (select menu_id from sys_menu where parent_id = 2000)
  );
