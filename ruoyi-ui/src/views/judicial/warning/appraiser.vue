<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true" label-width="90px">
      <el-form-item label="预警类型">
        <el-select v-model="queryParams.warningType" placeholder="请选择预警类型">
          <el-option label="即将到期" value="EXPIRING" />
          <el-option label="已过期" value="EXPIRED" />
        </el-select>
      </el-form-item>

      <el-form-item label="姓名">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="执业证号">
        <el-input
          v-model="queryParams.practiceCertNo"
          placeholder="请输入执业证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="执业类别">
        <el-input
          v-model="queryParams.practiceCategory"
          placeholder="请输入执业类别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleQuery">
          搜索
        </el-button>
        <el-button size="mini" icon="el-icon-refresh" @click="resetQuery">
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="warningList">
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="执业证号" align="center" prop="practiceCertNo" min-width="140" />
      <el-table-column label="所属机构" align="center" prop="institutionName" min-width="160" />
      <el-table-column label="执业类别" align="center" prop="practiceCategory" />
      <el-table-column label="有效期止" align="center" prop="certValidTo" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="预警类型" align="center" prop="warningType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.warningType === 'EXPIRING'" type="warning">即将到期</el-tag>
          <el-tag v-else-if="scope.row.warningType === 'EXPIRED'" type="danger">已过期</el-tag>
          <span v-else>{{ scope.row.warningType }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { listAppraiserWarning } from '@/api/judicial/warning'

export default {
  name: 'AppraiserWarning',
  data() {
    return {
      loading: false,
      warningList: [],
      queryParams: {
        warningType: 'EXPIRING',
        name: null,
        practiceCertNo: null,
        practiceCategory: null
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
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.getList()
    },
    resetQuery() {
      this.queryParams = {
        warningType: 'EXPIRING',
        name: null,
        practiceCertNo: null,
        practiceCategory: null
      }
      this.getList()
    }
  }
}
</script>