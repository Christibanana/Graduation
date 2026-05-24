-- 司法鉴定注册角色与菜单权限初始化脚本
-- 作用：
-- 1. 开启账号自助注册
-- 2. 创建“司法鉴定人”“司法鉴定机构”两个注册角色
-- 3. 两个角色仅授予“司法鉴定管理”菜单及其子菜单/按钮权限
-- 4. 初始化司法鉴定管理菜单，保证新电脑导入数据库后能看到业务菜单

set names utf8mb4;

insert into sys_menu
    (menu_id, menu_name, parent_id, order_num, path, component, `query`, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values
    (2000, '司法鉴定管理', 0, 4, 'judicial', null, '', 'Judicial', 1, 0, 'M', '0', '0', '', 'tree', 'admin', sysdate(), '', null, '司法鉴定管理目录'),
    (2001, '司法鉴定机构', 2000, 1, 'institution', 'system/institution/index', '', 'JdInstitution', 1, 0, 'C', '0', '0', 'system:institution:list', 'tree', 'admin', sysdate(), '', null, '司法鉴定机构菜单'),
    (2002, '司法鉴定机构查询', 2001, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:institution:query', '#', 'admin', sysdate(), '', null, ''),
    (2003, '司法鉴定机构新增', 2001, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:institution:add', '#', 'admin', sysdate(), '', null, ''),
    (2004, '司法鉴定机构修改', 2001, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:institution:edit', '#', 'admin', sysdate(), '', null, ''),
    (2005, '司法鉴定机构删除', 2001, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:institution:remove', '#', 'admin', sysdate(), '', null, ''),
    (2006, '司法鉴定机构导出', 2001, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:institution:export', '#', 'admin', sysdate(), '', null, ''),
    (2007, '司法鉴定人', 2000, 2, 'appraiser', 'judicial/appraiser/index', '', 'JdAppraiser', 1, 0, 'C', '0', '0', 'judicial:appraiser:list', 'user', 'admin', sysdate(), '', null, '司法鉴定人菜单'),
    (2008, '司法鉴定人查询', 2007, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:appraiser:query', '#', 'admin', sysdate(), '', null, ''),
    (2009, '司法鉴定人新增', 2007, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:appraiser:add', '#', 'admin', sysdate(), '', null, ''),
    (2010, '司法鉴定人修改', 2007, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:appraiser:edit', '#', 'admin', sysdate(), '', null, ''),
    (2011, '司法鉴定人删除', 2007, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:appraiser:remove', '#', 'admin', sysdate(), '', null, ''),
    (2012, '司法鉴定人导出', 2007, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:appraiser:export', '#', 'admin', sysdate(), '', null, ''),
    (2013, '申请审批', 2000, 3, 'application', 'judicial/application/index', '', 'JdApplication', 1, 0, 'C', '0', '0', 'judicial:application:list', 'form', 'admin', sysdate(), '', null, '司法鉴定申请审批菜单'),
    (2014, '申请审批查询', 2013, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:application:query', '#', 'admin', sysdate(), '', null, ''),
    (2015, '申请审批新增', 2013, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:application:add', '#', 'admin', sysdate(), '', null, ''),
    (2016, '申请审批修改', 2013, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:application:edit', '#', 'admin', sysdate(), '', null, ''),
    (2017, '申请审批删除', 2013, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:application:remove', '#', 'admin', sysdate(), '', null, ''),
    (2018, '申请审批导出', 2013, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'judicial:application:export', '#', 'admin', sysdate(), '', null, ''),
    (2019, '机构证照预警', 2000, 4, 'institutionWarning', 'judicial/warning/institution', '', 'JdInstitutionWarning', 1, 0, 'C', '0', '0', 'judicial:warning:institution', 'warning', 'admin', sysdate(), '', null, '机构证照预警菜单'),
    (2020, '鉴定人证照预警', 2000, 5, 'appraiserWarning', 'judicial/warning/appraiser', '', 'JdAppraiserWarning', 1, 0, 'C', '0', '0', 'judicial:warning:appraiser', 'warning', 'admin', sysdate(), '', null, '鉴定人证照预警菜单')
on duplicate key update
    menu_name = values(menu_name),
    parent_id = values(parent_id),
    order_num = values(order_num),
    path = values(path),
    component = values(component),
    route_name = values(route_name),
    menu_type = values(menu_type),
    visible = values(visible),
    status = values(status),
    perms = values(perms),
    icon = values(icon),
    update_by = 'admin',
    update_time = sysdate(),
    remark = values(remark);

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
