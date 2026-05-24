-- ----------------------------
-- 江西省司法鉴定机构鉴定人管理系统演示数据补充脚本
-- 本脚本用于补充演示数据。
-- 执行前应先导入 sql/jd_appraiser_system.sql。
-- ----------------------------

-- ----------------------------
-- 1、清理本脚本演示数据，保证可重复执行
-- ----------------------------
delete from jd_approval_record where id >= 100;
delete from jd_application where id >= 100;
delete from jd_appraiser where id >= 100;
delete from jd_institution where id >= 100;

-- ----------------------------
-- 2、司法鉴定机构演示数据
-- 包含正常、90天内即将到期、已经过期等场景
-- ----------------------------
insert into jd_institution
    (id, name, license_no, credit_code, legal_representative, principal, region, address, phone, business_scope,
     license_valid_from, license_valid_to, status, create_by, create_time, update_by, update_time, remark, del_flag)
values
    (100, '南昌弘正司法鉴定中心', '赣司鉴许字100号', '91360100MADEM0100A', '万志强', '罗文斌', '南昌市',
     '江西省南昌市东湖区阳明路168号', '0791-86321001', '法医临床鉴定、法医病理鉴定',
     DATE_SUB(CURDATE(), INTERVAL 360 DAY), DATE_ADD(CURDATE(), INTERVAL 900 DAY), 'NORMAL',
     'admin', sysdate(), 'admin', sysdate(), '演示正常机构', '0'),
    (101, '九江浔阳物证司法鉴定所', '赣司鉴许字101号', '91360400MADEM0101B', '邹建平', '熊海涛', '九江市',
     '江西省九江市浔阳区庐山南路96号', '0792-8213002', '物证鉴定、文书鉴定',
     DATE_SUB(CURDATE(), INTERVAL 330 DAY), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示即将到期机构', '0'),
    (102, '赣州章贡环境损害司法鉴定中心', '赣司鉴许字102号', '91360700MADEM0102C', '魏晓峰', '叶青', '赣州市',
     '江西省赣州市章贡区长征大道58号', '0797-8123003', '环境损害鉴定、污染物性质鉴定',
     DATE_SUB(CURDATE(), INTERVAL 350 DAY), DATE_ADD(CURDATE(), INTERVAL 60 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示即将到期机构', '0'),
    (103, '宜春明德司法鉴定所', '赣司鉴许字103号', '91360900MADEM0103D', '沈国华', '钟丽萍', '宜春市',
     '江西省宜春市袁州区明月北路188号', '0795-3213004', '法医临床鉴定、声像资料鉴定',
     DATE_SUB(CURDATE(), INTERVAL 320 DAY), DATE_ADD(CURDATE(), INTERVAL 85 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示即将到期机构', '0'),
    (104, '上饶信州司法鉴定中心', '赣司鉴许字104号', '91361100MADEM0104E', '丁立民', '夏秋', '上饶市',
     '江西省上饶市信州区凤凰大道66号', '0793-8213005', '法医临床鉴定、法医毒物鉴定',
     DATE_SUB(CURDATE(), INTERVAL 500 DAY), DATE_SUB(CURDATE(), INTERVAL 30 DAY), 'EXPIRED',
     'admin', sysdate(), '', null, '演示已过期机构', '0'),
    (105, '吉安庐陵司法鉴定所', '赣司鉴许字105号', '91360800MADEM0105F', '廖建军', '曾华', '吉安市',
     '江西省吉安市吉州区井冈山大道218号', '0796-8213006', '物证鉴定、痕迹鉴定',
     DATE_SUB(CURDATE(), INTERVAL 600 DAY), DATE_SUB(CURDATE(), INTERVAL 90 DAY), 'EXPIRED',
     'admin', sysdate(), '', null, '演示已过期机构', '0'),
    (106, '抚州临川司法鉴定中心', '赣司鉴许字106号', '91361000MADEM0106G', '宋志远', '章敏', '抚州市',
     '江西省抚州市临川区赣东大道369号', '0794-8213007', '法医病理鉴定、法医临床鉴定',
     DATE_SUB(CURDATE(), INTERVAL 700 DAY), DATE_SUB(CURDATE(), INTERVAL 120 DAY), 'EXPIRED',
     'admin', sysdate(), '', null, '演示已过期机构', '0'),
    (107, '景德镇瓷都司法鉴定所', '赣司鉴许字107号', '91360200MADEM0107H', '许庆峰', '邵颖', '景德镇市',
     '江西省景德镇市珠山区昌江大道88号', '0798-8213008', '声像资料鉴定、电子数据鉴定',
     DATE_SUB(CURDATE(), INTERVAL 260 DAY), DATE_ADD(CURDATE(), INTERVAL 760 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示正常机构', '0'),
    (108, '萍乡安源司法鉴定中心', '赣司鉴许字108号', '91360300MADEM0108J', '彭志勇', '胡琴', '萍乡市',
     '江西省萍乡市安源区建设东路128号', '0799-8213009', '法医临床鉴定、物证鉴定',
     DATE_SUB(CURDATE(), INTERVAL 180 DAY), DATE_ADD(CURDATE(), INTERVAL 880 DAY), 'NORMAL',
     'admin', sysdate(), 'admin', sysdate(), '演示机构新增申请已通过', '0');

-- ----------------------------
-- 3、司法鉴定人演示数据
-- 包含正常、90天内即将到期、已经过期等场景
-- ----------------------------
insert into jd_appraiser
    (id, institution_id, name, gender, id_card, education, professional_title, practice_cert_no, practice_category, phone,
     cert_valid_from, cert_valid_to, status, create_by, create_time, update_by, update_time, remark, del_flag)
values
    (100, 100, '胡建国', '0', '360102198204120317', '本科', '主任法医师', '赣司鉴人100号', 'FORENSIC_MEDICINE', '13979101001',
     DATE_SUB(CURDATE(), INTERVAL 360 DAY), DATE_ADD(CURDATE(), INTERVAL 900 DAY), 'NORMAL',
     'admin', sysdate(), 'admin', sysdate(), '演示正常鉴定人', '0'),
    (101, 101, '邓小兰', '1', '360403198511230428', '硕士', '副主任法医师', '赣司鉴人101号', 'FORENSIC_MEDICINE', '13979201002',
     DATE_SUB(CURDATE(), INTERVAL 330 DAY), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示即将到期鉴定人', '0'),
    (102, 104, '陈志鹏', '0', '361102197908050539', '本科', '高级工程师', '赣司鉴人102号', 'MATERIAL_EVIDENCE', '13979301003',
     DATE_SUB(CURDATE(), INTERVAL 500 DAY), DATE_SUB(CURDATE(), INTERVAL 30 DAY), 'EXPIRED',
     'admin', sysdate(), '', null, '演示已过期鉴定人', '0'),
    (103, 107, '谢文静', '1', '360202199001170640', '本科', '工程师', '赣司鉴人103号', 'AUDIO_VIDEO', '13979801004',
     DATE_SUB(CURDATE(), INTERVAL 200 DAY), DATE_ADD(CURDATE(), INTERVAL 720 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示正常鉴定人', '0'),
    (104, 102, '刘海峰', '0', '360702198710280751', '硕士', '高级工程师', '赣司鉴人104号', 'ENV_DAMAGE', '13979701005',
     DATE_SUB(CURDATE(), INTERVAL 320 DAY), DATE_ADD(CURDATE(), INTERVAL 60 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示即将到期鉴定人', '0'),
    (105, 105, '罗晓燕', '1', '360802198605140862', '本科', '高级工程师', '赣司鉴人105号', 'MATERIAL_EVIDENCE', '13979601006',
     DATE_SUB(CURDATE(), INTERVAL 650 DAY), DATE_SUB(CURDATE(), INTERVAL 90 DAY), 'EXPIRED',
     'admin', sysdate(), '', null, '演示已过期鉴定人', '0'),
    (106, 103, '周明远', '0', '360902198303090973', '本科', '副主任法医师', '赣司鉴人106号', 'FORENSIC_MEDICINE', '13979501007',
     DATE_SUB(CURDATE(), INTERVAL 260 DAY), DATE_ADD(CURDATE(), INTERVAL 820 DAY), 'NORMAL',
     'admin', sysdate(), '', null, '演示正常鉴定人', '0'),
    (107, 108, '袁佳敏', '1', '360302199212181084', '硕士', '工程师', '赣司鉴人107号', 'AUDIO_VIDEO', '13979901008',
     DATE_SUB(CURDATE(), INTERVAL 120 DAY), DATE_ADD(CURDATE(), INTERVAL 760 DAY), 'NORMAL',
     'admin', sysdate(), 'admin', sysdate(), '演示鉴定人新增申请已通过', '0');

-- ----------------------------
-- 4、申请演示数据
-- 覆盖 8 种申请类型和 DRAFT、SUBMITTED、APPROVED、REJECTED 状态
-- JSON 字段使用 Java 实体常见驼峰字段名
-- ----------------------------
insert into jd_application
    (id, apply_no, apply_type, object_type, target_id, applicant_id, institution_id, before_data, after_data, material_desc,
     status, submit_time, reviewer_id, review_time, review_opinion, create_by, create_time, update_by, update_time, remark, del_flag)
values
    (100, 'JDD202600100', 'INSTITUTION_REGISTER', 'INSTITUTION', 108, 2, 108,
     null,
     JSON_OBJECT('name', '萍乡安源司法鉴定中心', 'licenseNo', '赣司鉴许字108号', 'region', '萍乡市', 'phone', '0799-8213009',
                 'businessScope', '法医临床鉴定、物证鉴定', 'licenseValidTo', DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 880 DAY), '%Y-%m-%d'),
                 'status', 'NORMAL'),
     '机构新增登记材料齐全。', 'APPROVED', DATE_SUB(sysdate(), INTERVAL 20 DAY), 1, DATE_SUB(sysdate(), INTERVAL 18 DAY),
     '材料完整，准予登记。', 'admin', DATE_SUB(sysdate(), INTERVAL 22 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 18 DAY),
     '演示机构新增已通过', '0'),
    (101, 'JDD202600101', 'INSTITUTION_CHANGE', 'INSTITUTION', 100, 2, 100,
     JSON_OBJECT('name', '南昌弘正司法鉴定中心', 'phone', '0791-86321000', 'address', '江西省南昌市东湖区阳明路168号'),
     JSON_OBJECT('name', '南昌弘正司法鉴定中心', 'phone', '0791-86321001', 'address', '江西省南昌市东湖区阳明路168号',
                 'status', 'NORMAL'),
     '机构联系电话变更材料齐全。', 'APPROVED', DATE_SUB(sysdate(), INTERVAL 16 DAY), 1, DATE_SUB(sysdate(), INTERVAL 15 DAY),
     '同意变更联系电话。', 'admin', DATE_SUB(sysdate(), INTERVAL 17 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 15 DAY),
     '演示机构变更已通过', '0'),
    (102, 'JDD202600102', 'INSTITUTION_RENEWAL', 'INSTITUTION', 101, 3, 101,
     JSON_OBJECT('name', '九江浔阳物证司法鉴定所', 'licenseValidTo', DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 30 DAY), '%Y-%m-%d'),
                 'status', 'NORMAL'),
     JSON_OBJECT('name', '九江浔阳物证司法鉴定所', 'licenseValidTo', DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 1095 DAY), '%Y-%m-%d'),
                 'status', 'NORMAL'),
     '机构延续申请已提交，待审核。', 'SUBMITTED', DATE_SUB(sysdate(), INTERVAL 8 DAY), null, null, null,
     'admin', DATE_SUB(sysdate(), INTERVAL 9 DAY), '', null, '演示机构延续待审核', '0'),
    (103, 'JDD202600103', 'INSTITUTION_CANCEL', 'INSTITUTION', 102, 4, 102,
     JSON_OBJECT('name', '赣州章贡环境损害司法鉴定中心', 'status', 'NORMAL'),
     JSON_OBJECT('name', '赣州章贡环境损害司法鉴定中心', 'status', 'CANCELLED'),
     '机构注销申请材料缺少清算说明。', 'REJECTED', DATE_SUB(sysdate(), INTERVAL 10 DAY), 1, DATE_SUB(sysdate(), INTERVAL 9 DAY),
     '注销材料不完整，请补充清算说明。', 'admin', DATE_SUB(sysdate(), INTERVAL 11 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 9 DAY),
     '演示机构注销被驳回', '0'),
    (104, 'JDD202600104', 'APPRAISER_REGISTER', 'APPRAISER', 107, 5, 108,
     null,
     JSON_OBJECT('institutionId', 108, 'name', '袁佳敏', 'practiceCertNo', '赣司鉴人107号', 'practiceCategory', 'AUDIO_VIDEO',
                 'certValidTo', DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 760 DAY), '%Y-%m-%d'), 'status', 'NORMAL'),
     '鉴定人新增登记材料齐全。', 'APPROVED', DATE_SUB(sysdate(), INTERVAL 14 DAY), 1, DATE_SUB(sysdate(), INTERVAL 13 DAY),
     '符合执业登记条件。', 'admin', DATE_SUB(sysdate(), INTERVAL 15 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 13 DAY),
     '演示鉴定人新增已通过', '0'),
    (105, 'JDD202600105', 'APPRAISER_CHANGE', 'APPRAISER', 100, 6, 100,
     JSON_OBJECT('name', '胡建国', 'phone', '13979101000', 'professionalTitle', '副主任法医师'),
     JSON_OBJECT('name', '胡建国', 'phone', '13979101001', 'professionalTitle', '主任法医师', 'status', 'NORMAL'),
     '鉴定人职称和联系电话变更申请已提交。', 'SUBMITTED', DATE_SUB(sysdate(), INTERVAL 5 DAY), null, null, null,
     'admin', DATE_SUB(sysdate(), INTERVAL 6 DAY), '', null, '演示鉴定人变更待审核', '0'),
    (106, 'JDD202600106', 'APPRAISER_RENEWAL', 'APPRAISER', 101, 7, 101,
     JSON_OBJECT('name', '邓小兰', 'certValidTo', DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 30 DAY), '%Y-%m-%d'), 'status', 'NORMAL'),
     JSON_OBJECT('name', '邓小兰', 'certValidTo', DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 1095 DAY), '%Y-%m-%d'), 'status', 'NORMAL'),
     '鉴定人延续申请缺少继续教育证明。', 'REJECTED', DATE_SUB(sysdate(), INTERVAL 7 DAY), 1, DATE_SUB(sysdate(), INTERVAL 6 DAY),
     '请补充继续教育证明后重新提交。', 'admin', DATE_SUB(sysdate(), INTERVAL 8 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 6 DAY),
     '演示鉴定人延续被驳回', '0'),
    (107, 'JDD202600107', 'APPRAISER_CANCEL', 'APPRAISER', 102, 8, 104,
     JSON_OBJECT('name', '陈志鹏', 'status', 'EXPIRED'),
     JSON_OBJECT('name', '陈志鹏', 'status', 'CANCELLED'),
     '鉴定人注销申请草稿，尚未提交。', 'DRAFT', null, null, null, null,
     'admin', sysdate(), '', null, '演示鉴定人注销草稿', '0');

-- ----------------------------
-- 5、审批记录演示数据
-- 对 SUBMITTED、APPROVED、REJECTED 申请补充对应审批过程记录
-- ----------------------------
insert into jd_approval_record
    (id, application_id, action, operator_id, opinion, operate_time, create_by, create_time, update_by, update_time, remark, del_flag)
values
    (100, 100, 'SUBMIT', 2, '提交机构新增登记申请。', DATE_SUB(sysdate(), INTERVAL 20 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 20 DAY), '', null, '演示提交记录', '0'),
    (101, 100, 'APPROVE', 1, '材料完整，准予登记。', DATE_SUB(sysdate(), INTERVAL 18 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 18 DAY), '', null, '演示通过记录', '0'),
    (102, 101, 'SUBMIT', 2, '提交机构变更申请。', DATE_SUB(sysdate(), INTERVAL 16 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 16 DAY), '', null, '演示提交记录', '0'),
    (103, 101, 'APPROVE', 1, '同意变更联系电话。', DATE_SUB(sysdate(), INTERVAL 15 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 15 DAY), '', null, '演示通过记录', '0'),
    (104, 102, 'SUBMIT', 3, '提交机构延续申请。', DATE_SUB(sysdate(), INTERVAL 8 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 8 DAY), '', null, '演示待审核提交记录', '0'),
    (105, 103, 'SUBMIT', 4, '提交机构注销申请。', DATE_SUB(sysdate(), INTERVAL 10 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 10 DAY), '', null, '演示提交记录', '0'),
    (106, 103, 'REJECT', 1, '注销材料不完整，请补充清算说明。', DATE_SUB(sysdate(), INTERVAL 9 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 9 DAY), '', null, '演示驳回记录', '0'),
    (107, 104, 'SUBMIT', 5, '提交鉴定人新增登记申请。', DATE_SUB(sysdate(), INTERVAL 14 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 14 DAY), '', null, '演示提交记录', '0'),
    (108, 104, 'APPROVE', 1, '符合执业登记条件。', DATE_SUB(sysdate(), INTERVAL 13 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 13 DAY), '', null, '演示通过记录', '0'),
    (109, 105, 'SUBMIT', 6, '提交鉴定人变更申请。', DATE_SUB(sysdate(), INTERVAL 5 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 5 DAY), '', null, '演示待审核提交记录', '0'),
    (110, 106, 'SUBMIT', 7, '提交鉴定人延续申请。', DATE_SUB(sysdate(), INTERVAL 7 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 7 DAY), '', null, '演示提交记录', '0'),
    (111, 106, 'REJECT', 1, '请补充继续教育证明后重新提交。', DATE_SUB(sysdate(), INTERVAL 6 DAY), 'admin', DATE_SUB(sysdate(), INTERVAL 6 DAY), '', null, '演示驳回记录', '0');
