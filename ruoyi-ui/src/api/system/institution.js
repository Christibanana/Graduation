import request from '@/utils/request'

// 查询司法鉴定机构列表
export function listInstitution(query) {
  return request({
    url: '/system/institution/list',
    method: 'get',
    params: query
  })
}

// 查询司法鉴定机构详细
export function getInstitution(id) {
  return request({
    url: '/system/institution/' + id,
    method: 'get'
  })
}

// 新增司法鉴定机构
export function addInstitution(data) {
  return request({
    url: '/system/institution',
    method: 'post',
    data: data
  })
}

// 修改司法鉴定机构
export function updateInstitution(data) {
  return request({
    url: '/system/institution',
    method: 'put',
    data: data
  })
}

// 删除司法鉴定机构
export function delInstitution(id) {
  return request({
    url: '/system/institution/' + id,
    method: 'delete'
  })
}
