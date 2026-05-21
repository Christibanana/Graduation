import request from '@/utils/request'

// 查询司法鉴定人列表
export function listAppraiser(query) {
  return request({
    url: '/judicial/appraiser/list',
    method: 'get',
    params: query
  })
}

// 查询司法鉴定人详细
export function getAppraiser(id) {
  return request({
    url: '/judicial/appraiser/' + id,
    method: 'get'
  })
}

// 新增司法鉴定人
export function addAppraiser(data) {
  return request({
    url: '/judicial/appraiser',
    method: 'post',
    data: data
  })
}

// 修改司法鉴定人
export function updateAppraiser(data) {
  return request({
    url: '/judicial/appraiser',
    method: 'put',
    data: data
  })
}

// 删除司法鉴定人
export function delAppraiser(id) {
  return request({
    url: '/judicial/appraiser/' + id,
    method: 'delete'
  })
}
