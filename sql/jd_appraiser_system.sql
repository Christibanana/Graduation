-- ----------------------------
-- 江西省司法鉴定机构鉴定人管理系统业务初始化脚本
-- ----------------------------

-- ----------------------------
-- 1、司法鉴定机构正式档案表
-- ----------------------------
drop table if exists jd_institution;

create table jd_institution (
    id bigint(20) not null auto_increment comment '机构ID',
    name varchar(100) not null comment '机构名称',
    license_no varchar(100) not null comment '许可证号',
    credit_code varchar(100) default null comment '统一社会信用代码',
    legal_representative varchar(50) default null comment '法定代表人',
    principal varchar(50) default null comment '机构负责人',
    region varchar(50) default null comment '所属地区',
    address varchar(255) default null comment '住所',
    phone varchar(50) default null comment '联系电话',
    business_scope varchar(255) default null comment '业务范围',
    license_valid_from date comment '许可证有效期起',
    license_valid_to date comment '许可证有效期止',
    status varchar(20) default 'NORMAL' comment '机构状态',
    create_by varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark varchar(500) default null comment '备注',
    del_flag char(1) default '0' comment '删除标志（0代表存在 2代表删除）',
    primary key (id),
    unique key uk_jd_institution_license_no (license_no),
    key idx_jd_institution_region (region),
    key idx_jd_institution_status (status)
) engine = innodb auto_increment = 100 comment = '司法鉴定机构正式档案表';

-- ----------------------------
-- 2、司法鉴定人正式档案表
-- ----------------------------
drop table if exists jd_appraiser;

create table jd_appraiser (
    id bigint(20) not null auto_increment comment '鉴定人ID',
    institution_id bigint(20) not null comment '所属机构ID',
    name varchar(50) not null comment '姓名',
    gender char(1) default null comment '性别',
    id_card varchar(32) default null comment '身份证号',
    education varchar(50) default null comment '学历',
    professional_title varchar(100) default null comment '专业技术职称',
    practice_cert_no varchar(100) not null comment '执业证号',
    practice_category varchar(100) default null comment '执业类别',
    phone varchar(50) default null comment '联系电话',
    cert_valid_from date comment '执业证有效期起',
    cert_valid_to date comment '执业证有效期止',
    status varchar(20) default 'NORMAL' comment '鉴定人状态',
    create_by varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark varchar(500) default null comment '备注',
    del_flag char(1) default '0' comment '删除标志（0代表存在 2代表删除）',
    primary key (id),
    unique key uk_jd_appraiser_cert_no (practice_cert_no),
    key idx_jd_appraiser_institution_id (institution_id),
    key idx_jd_appraiser_status (status)
) engine = innodb auto_increment = 100 comment = '司法鉴定人正式档案表';

-- ----------------------------
-- 3、登记、变更、延续、注销申请表
-- ----------------------------
drop table if exists jd_application;

create table jd_application (
    id bigint(20) not null auto_increment comment '申请ID',
    apply_no varchar(64) not null comment '申请编号',
    apply_type varchar(50) not null comment '申请类型',
    object_type varchar(20) not null comment '申请对象类型',
    target_id bigint(20) default null comment '目标对象ID',
    applicant_id bigint(20) default null comment '申请人ID',
    institution_id bigint(20) default null comment '所属机构ID',
    before_data longtext comment '变更前数据JSON',
    after_data longtext comment '变更后数据JSON',
    material_desc varchar(1000) default null comment '申请材料说明',
    status varchar(20) default 'DRAFT' comment '申请状态',
    submit_time datetime comment '提交时间',
    reviewer_id bigint(20) default null comment '审核人ID',
    review_time datetime comment '审核时间',
    review_opinion varchar(1000) default null comment '审核意见',
    create_by varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark varchar(500) default null comment '备注',
    del_flag char(1) default '0' comment '删除标志（0代表存在 2代表删除）',
    primary key (id),
    unique key uk_jd_application_apply_no (apply_no),
    key idx_jd_application_status (status),
    key idx_jd_application_institution_id (institution_id),
    key idx_jd_application_target_id (target_id)
) engine = innodb auto_increment = 100 comment = '登记、变更、延续、注销申请表';

-- ----------------------------
-- 4、审批记录表
-- ----------------------------
drop table if exists jd_approval_record;

create table jd_approval_record (
    id bigint(20) not null auto_increment comment '审批记录ID',
    application_id bigint(20) not null comment '申请ID',
    action varchar(20) not null comment '审批动作',
    operator_id bigint(20) default null comment '操作人ID',
    opinion varchar(1000) default null comment '处理意见',
    operate_time datetime comment '操作时间',
    create_by varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark varchar(500) default null comment '备注',
    del_flag char(1) default '0' comment '删除标志（0代表存在 2代表删除）',
    primary key (id),
    key idx_jd_approval_record_application_id (application_id),
    key idx_jd_approval_record_action (action)
) engine = innodb auto_increment = 100 comment = '审批记录表';

-- ----------------------------
-- 5、司法鉴定业务字典类型
-- ----------------------------
delete from sys_dict_data
where
    dict_type in (
        'jd_practice_category',
        'jd_region',
        'jd_institution_status',
        'jd_appraiser_status',
        'jd_application_status',
        'jd_application_type'
    );

delete from sys_dict_type
where
    dict_type in (
        'jd_practice_category',
        'jd_region',
        'jd_institution_status',
        'jd_appraiser_status',
        'jd_application_status',
        'jd_application_type'
    );

insert into
    sys_dict_type
values (
        100,
        '司法鉴定执业类别',
        'jd_practice_category',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '司法鉴定执业类别列表'
    );

insert into
    sys_dict_type
values (
        101,
        '江西省设区市',
        'jd_region',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '江西省设区市列表'
    );

insert into
    sys_dict_type
values (
        102,
        '机构状态',
        'jd_institution_status',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '司法鉴定机构状态列表'
    );

insert into
    sys_dict_type
values (
        103,
        '鉴定人状态',
        'jd_appraiser_status',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '司法鉴定人状态列表'
    );

insert into
    sys_dict_type
values (
        104,
        '申请状态',
        'jd_application_status',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '司法鉴定申请状态列表'
    );

insert into
    sys_dict_type
values (
        105,
        '申请类型',
        'jd_application_type',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '司法鉴定申请类型列表'
    );

-- ----------------------------
-- 6、司法鉴定业务字典数据
-- ----------------------------
insert into
    sys_dict_data
values (
        1000,
        1,
        '法医类',
        'FORENSIC_MEDICINE',
        'jd_practice_category',
        '',
        'primary',
        'Y',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '法医类'
    );

insert into
    sys_dict_data
values (
        1001,
        2,
        '物证类',
        'MATERIAL_EVIDENCE',
        'jd_practice_category',
        '',
        'success',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '物证类'
    );

insert into
    sys_dict_data
values (
        1002,
        3,
        '声像资料',
        'AUDIO_VIDEO',
        'jd_practice_category',
        '',
        'warning',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '声像资料'
    );

insert into
    sys_dict_data
values (
        1003,
        4,
        '环境损害',
        'ENV_DAMAGE',
        'jd_practice_category',
        '',
        'info',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '环境损害'
    );

insert into
    sys_dict_data
values (
        1010,
        1,
        '南昌市',
        '南昌市',
        'jd_region',
        '',
        'primary',
        'Y',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '南昌市'
    );

insert into
    sys_dict_data
values (
        1011,
        2,
        '九江市',
        '九江市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '九江市'
    );

insert into
    sys_dict_data
values (
        1012,
        3,
        '景德镇市',
        '景德镇市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '景德镇市'
    );

insert into
    sys_dict_data
values (
        1013,
        4,
        '萍乡市',
        '萍乡市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '萍乡市'
    );

insert into
    sys_dict_data
values (
        1014,
        5,
        '新余市',
        '新余市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '新余市'
    );

insert into
    sys_dict_data
values (
        1015,
        6,
        '鹰潭市',
        '鹰潭市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '鹰潭市'
    );

insert into
    sys_dict_data
values (
        1016,
        7,
        '赣州市',
        '赣州市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '赣州市'
    );

insert into
    sys_dict_data
values (
        1017,
        8,
        '宜春市',
        '宜春市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '宜春市'
    );

insert into
    sys_dict_data
values (
        1018,
        9,
        '上饶市',
        '上饶市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '上饶市'
    );

insert into
    sys_dict_data
values (
        1019,
        10,
        '吉安市',
        '吉安市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '吉安市'
    );

insert into
    sys_dict_data
values (
        1020,
        11,
        '抚州市',
        '抚州市',
        'jd_region',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '抚州市'
    );

insert into
    sys_dict_data
values (
        1030,
        1,
        '正常',
        'NORMAL',
        'jd_institution_status',
        '',
        'primary',
        'Y',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '正常'
    );

insert into
    sys_dict_data
values (
        1031,
        2,
        '证照过期',
        'EXPIRED',
        'jd_institution_status',
        '',
        'warning',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '证照过期'
    );

insert into
    sys_dict_data
values (
        1032,
        3,
        '已注销',
        'CANCELLED',
        'jd_institution_status',
        '',
        'danger',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '已注销'
    );

insert into
    sys_dict_data
values (
        1040,
        1,
        '正常',
        'NORMAL',
        'jd_appraiser_status',
        '',
        'primary',
        'Y',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '正常'
    );

insert into
    sys_dict_data
values (
        1041,
        2,
        '证照过期',
        'EXPIRED',
        'jd_appraiser_status',
        '',
        'warning',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '证照过期'
    );

insert into
    sys_dict_data
values (
        1042,
        3,
        '已注销',
        'CANCELLED',
        'jd_appraiser_status',
        '',
        'danger',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '已注销'
    );

insert into
    sys_dict_data
values (
        1050,
        1,
        '草稿',
        'DRAFT',
        'jd_application_status',
        '',
        'info',
        'Y',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '草稿'
    );

insert into
    sys_dict_data
values (
        1051,
        2,
        '待审核',
        'SUBMITTED',
        'jd_application_status',
        '',
        'warning',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '待审核'
    );

insert into
    sys_dict_data
values (
        1052,
        3,
        '审核通过',
        'APPROVED',
        'jd_application_status',
        '',
        'success',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '审核通过'
    );

insert into
    sys_dict_data
values (
        1053,
        4,
        '审核驳回',
        'REJECTED',
        'jd_application_status',
        '',
        'danger',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '审核驳回'
    );

insert into
    sys_dict_data
values (
        1060,
        1,
        '机构新增',
        'INSTITUTION_REGISTER',
        'jd_application_type',
        '',
        'primary',
        'Y',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '机构新增'
    );

insert into
    sys_dict_data
values (
        1061,
        2,
        '机构变更',
        'INSTITUTION_CHANGE',
        'jd_application_type',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '机构变更'
    );

insert into
    sys_dict_data
values (
        1062,
        3,
        '机构延续',
        'INSTITUTION_RENEWAL',
        'jd_application_type',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '机构延续'
    );

insert into
    sys_dict_data
values (
        1063,
        4,
        '机构注销',
        'INSTITUTION_CANCEL',
        'jd_application_type',
        '',
        'primary',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '机构注销'
    );

insert into
    sys_dict_data
values (
        1064,
        5,
        '鉴定人新增',
        'APPRAISER_REGISTER',
        'jd_application_type',
        '',
        'success',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '鉴定人新增'
    );

insert into
    sys_dict_data
values (
        1065,
        6,
        '鉴定人变更',
        'APPRAISER_CHANGE',
        'jd_application_type',
        '',
        'success',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '鉴定人变更'
    );

insert into
    sys_dict_data
values (
        1066,
        7,
        '鉴定人延续',
        'APPRAISER_RENEWAL',
        'jd_application_type',
        '',
        'success',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '鉴定人延续'
    );

insert into
    sys_dict_data
values (
        1067,
        8,
        '鉴定人注销',
        'APPRAISER_CANCEL',
        'jd_application_type',
        '',
        'success',
        'N',
        '0',
        'admin',
        sysdate(),
        '',
        null,
        '鉴定人注销'
    );

-- ----------------------------
-- 7、司法鉴定机构测试数据
-- ----------------------------
insert into
    jd_institution
values (
        1,
        '江西求实司法鉴定中心',
        '赣司鉴许字001号',
        '91360100MA00000001',
        '刘建华',
        '王明',
        '南昌市',
        '江西省南昌市红谷滩区赣江中大道100号',
        '0791-88880001',
        '法医临床鉴定、法医病理鉴定',
        '2024-01-01',
        '2029-01-01',
        'NORMAL',
        'admin',
        sysdate(),
        '',
        null,
        '模拟测试机构',
        '0'
    );

insert into
    jd_institution
values (
        2,
        '九江明鉴司法鉴定所',
        '赣司鉴许字002号',
        '91360400MA00000002',
        '陈志强',
        '李娜',
        '九江市',
        '江西省九江市浔阳区长虹大道88号',
        '0792-88880002',
        '物证鉴定、文书鉴定',
        '2023-06-01',
        '2028-06-01',
        'NORMAL',
        'admin',
        sysdate(),
        '',
        null,
        '模拟测试机构',
        '0'
    );

insert into
    jd_institution
values (
        3,
        '赣南环境损害司法鉴定中心',
        '赣司鉴许字003号',
        '91360700MA00000003',
        '黄晓军',
        '张敏',
        '赣州市',
        '江西省赣州市章贡区文明大道66号',
        '0797-88880003',
        '环境损害鉴定、声像资料鉴定',
        '2021-07-01',
        '2026-07-01',
        'EXPIRED',
        'admin',
        sysdate(),
        '',
        null,
        '证照预警测试机构',
        '0'
    );

-- ----------------------------
-- 8、司法鉴定人测试数据
-- ----------------------------
insert into
    jd_appraiser
values (
        1,
        1,
        '张伟',
        '0',
        '360102198001010011',
        '本科',
        '主任法医师',
        '赣司鉴人001号',
        'FORENSIC_MEDICINE',
        '13800000001',
        '2024-01-01',
        '2029-01-01',
        'NORMAL',
        'admin',
        sysdate(),
        '',
        null,
        '模拟测试鉴定人',
        '0'
    );

insert into
    jd_appraiser
values (
        2,
        1,
        '李芳',
        '1',
        '360102198203150022',
        '硕士',
        '副主任法医师',
        '赣司鉴人002号',
        'FORENSIC_MEDICINE',
        '13800000002',
        '2024-01-01',
        '2029-01-01',
        'NORMAL',
        'admin',
        sysdate(),
        '',
        null,
        '模拟测试鉴定人',
        '0'
    );

insert into
    jd_appraiser
values (
        3,
        2,
        '王强',
        '0',
        '360403197912120033',
        '本科',
        '高级工程师',
        '赣司鉴人003号',
        'MATERIAL_EVIDENCE',
        '13800000003',
        '2023-06-01',
        '2028-06-01',
        'NORMAL',
        'admin',
        sysdate(),
        '',
        null,
        '模拟测试鉴定人',
        '0'
    );

insert into
    jd_appraiser
values (
        4,
        2,
        '赵敏',
        '1',
        '360403198506060044',
        '本科',
        '工程师',
        '赣司鉴人004号',
        'MATERIAL_EVIDENCE',
        '13800000004',
        '2023-06-01',
        '2028-06-01',
        'NORMAL',
        'admin',
        sysdate(),
        '',
        null,
        '模拟测试鉴定人',
        '0'
    );

insert into
    jd_appraiser
values (
        5,
        3,
        '周磊',
        '0',
        '360702198811110055',
        '硕士',
        '高级工程师',
        '赣司鉴人005号',
        'ENV_DAMAGE',
        '13800000005',
        '2021-05-01',
        '2026-05-01',
        'EXPIRED',
        'admin',
        sysdate(),
        '',
        null,
        '证照预警测试鉴定人',
        '0'
    );

insert into
    jd_appraiser
values (
        6,
        3,
        '孙洁',
        '1',
        '360702199003030066',
        '本科',
        '工程师',
        '赣司鉴人006号',
        'AUDIO_VIDEO',
        '13800000006',
        '2022-03-01',
        '2027-03-01',
        'NORMAL',
        'admin',
        sysdate(),
        '',
        null,
        '模拟测试鉴定人',
        '0'
    );

-- ----------------------------
-- 9、申请测试数据
-- ----------------------------
insert into
    jd_application
values (
        1,
        'JD202605220001',
        'INSTITUTION_CHANGE',
        'INSTITUTION',
        1,
        1,
        1,
        '{"phone":"0791-88880001"}',
        '{"phone":"0791-88889999"}',
        '机构联系电话变更申请材料齐全。',
        'SUBMITTED',
        sysdate(),
        null,
        null,
        null,
        'admin',
        sysdate(),
        '',
        null,
        '待审核申请',
        '0'
    );

insert into
    jd_application
values (
        2,
        'JD202605220002',
        'APPRAISER_RENEWAL',
        'APPRAISER',
        5,
        2,
        3,
        '{"cert_valid_to":"2026-05-01"}',
        '{"cert_valid_to":"2031-05-01"}',
        '鉴定人执业证延续申请材料齐全。',
        'SUBMITTED',
        sysdate(),
        null,
        null,
        null,
        'admin',
        sysdate(),
        '',
        null,
        '待审核申请',
        '0'
    );

insert into
    jd_application
values (
        3,
        'JD202605220003',
        'APPRAISER_REGISTER',
        'APPRAISER',
        6,
        3,
        3,
        null,
        '{"institution_id":3,"name":"孙洁","gender":"1","id_card":"360702199003030066","education":"本科","professional_title":"工程师","practice_cert_no":"赣司鉴人006号","practice_category":"AUDIO_VIDEO","phone":"13800000006","cert_valid_from":"2022-03-01","cert_valid_to":"2027-03-01","status":"NORMAL"}',
        '鉴定人新增申请已通过。',
        'APPROVED',
        '2026-05-20 09:30:00',
        1,
        '2026-05-20 15:00:00',
        '材料完整，符合登记条件。',
        'admin',
        sysdate(),
        'admin',
        sysdate(),
        '审核通过申请',
        '0'
    );

insert into
    jd_application
values (
        4,
        'JD202605220004',
        'INSTITUTION_RENEWAL',
        'INSTITUTION',
        3,
        4,
        3,
        '{"name":"赣南环境损害司法鉴定中心","license_no":"赣司鉴许字003号","license_valid_from":"2021-05-01","license_valid_to":"2026-05-01","status":"EXPIRED"}',
        '{"name":"赣南环境损害司法鉴定中心","license_no":"赣司鉴许字003号","credit_code":"91360700MA00000003","legal_representative":"黄晓军","principal":"张敏","region":"赣州市","address":"江西省赣州市章贡区文明大道66号","phone":"0797-88880003","business_scope":"环境损害鉴定、声像资料鉴定","license_valid_from":"2026-05-02","license_valid_to":"2031-05-01","status":"NORMAL"}',
        '机构延续申请材料缺少场所证明。',
        'REJECTED',
        '2026-05-21 10:00:00',
        1,
        '2026-05-21 16:20:00',
        '申请材料不完整，请补充场所证明后重新提交。',
        'admin',
        sysdate(),
        'admin',
        sysdate(),
        '审核驳回申请',
        '0'
    );

-- ----------------------------
-- 10、审批记录测试数据
-- ----------------------------
insert into
    jd_approval_record
values (
        1,
        1,
        'SUBMIT',
        1,
        '提交机构变更申请。',
        sysdate(),
        'admin',
        sysdate(),
        '',
        null,
        '待审核提交记录',
        '0'
    );

insert into
    jd_approval_record
values (
        2,
        2,
        'SUBMIT',
        2,
        '提交鉴定人延续申请。',
        sysdate(),
        'admin',
        sysdate(),
        '',
        null,
        '待审核提交记录',
        '0'
    );

insert into
    jd_approval_record
values (
        3,
        3,
        'APPROVE',
        1,
        '材料完整，符合登记条件。',
        '2026-05-20 15:00:00',
        'admin',
        sysdate(),
        '',
        null,
        '审核通过记录',
        '0'
    );

insert into
    jd_approval_record
values (
        4,
        4,
        'REJECT',
        1,
        '申请材料不完整，请补充场所证明后重新提交。',
        '2026-05-21 16:20:00',
        'admin',
        sysdate(),
        '',
        null,
        '审核驳回记录',
        '0'
    );