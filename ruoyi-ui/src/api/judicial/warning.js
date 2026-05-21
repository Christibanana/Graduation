import request from '@/utils/request'

// 查询机构证照预警列表
export function listInstitutionWarning(query) {
  return request({
    url: '/judicial/warning/institution/list',
    method: 'get',
    params: query
  })
}

// 查询鉴定人证照预警列表
export function listAppraiserWarning(query) {
  return request({
    url: '/judicial/warning/appraiser/list',
    method: 'get',
    params: query
  })
}