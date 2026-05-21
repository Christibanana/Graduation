import request from '@/utils/request'

// 查询申请审批列表
export function listApplication(query) {
  return request({
    url: '/judicial/application/list',
    method: 'get',
    params: query
  })
}

// 查询申请审批详细
export function getApplication(id) {
  return request({
    url: '/judicial/application/' + id,
    method: 'get'
  })
}

// 新增申请审批
export function addApplication(data) {
  return request({
    url: '/judicial/application',
    method: 'post',
    data: data
  })
}

// 修改申请审批
export function updateApplication(data) {
  return request({
    url: '/judicial/application',
    method: 'put',
    data: data
  })
}

// 删除申请审批
export function delApplication(id) {
  return request({
    url: '/judicial/application/' + id,
    method: 'delete'
  })
}

// 提交申请审批
export function submitApplication(id) {
  return request({
    url: '/judicial/application/submit/' + id,
    method: 'post'
  })
}

// 审核通过申请审批
export function approveApplication(id, data) {
  return request({
    url: '/judicial/application/approve/' + id,
    method: 'post',
    data: data
  })
}

// 审核驳回申请审批
export function rejectApplication(id, data) {
  return request({
    url: '/judicial/application/reject/' + id,
    method: 'post',
    data: data
  })
}
