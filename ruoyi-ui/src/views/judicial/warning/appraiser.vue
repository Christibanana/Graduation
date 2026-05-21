<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="100px">
      <el-form-item label="预警类型" prop="warningType">
        <el-select v-model="queryParams.warningType" placeholder="请选择预警类型" clearable>
          <el-option label="即将到期" value="EXPIRING" />
          <el-option label="已经过期" value="EXPIRED" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="warningList">
      <el-table-column label="姓名" align="center" prop="name" width="100" />
      <el-table-column label="执业证号" align="center" prop="practiceCertNo" min-width="140" :show-overflow-tooltip="true" />
      <el-table-column label="所属机构ID" align="center" prop="institutionId" width="110" />
      <el-table-column label="所属机构" align="center" prop="institutionName" min-width="160" :show-overflow-tooltip="true" />
      <el-table-column label="执业类别" align="center" prop="practiceCategory" min-width="120" />
      <el-table-column label="有效期止" align="center" prop="certValidTo" width="120" />
      <el-table-column label="状态" align="center" prop="status" width="100" />
      <el-table-column label="预警类型" align="center" prop="warningType" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.warningType === 'EXPIRING'" type="warning">即将到期</el-tag>
          <el-tag v-else-if="scope.row.warningType === 'EXPIRED'" type="danger">已经过期</el-tag>
          <span v-else>{{ scope.row.warningType }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listAppraiserWarning } from '@/api/judicial/warning'

export default {
  name: 'AppraiserWarning',
  data() {
    return {
      loading: false,
      total: 0,
      warningList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warningType: 'EXPIRING'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listAppraiserWarning(this.queryParams).then(response => {
        this.warningList = response.rows || []
        this.total = response.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.pageNum = 1
      this.getList()
    }
  }
}
</script>
