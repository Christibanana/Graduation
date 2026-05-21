<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请编号" prop="applyNo">
        <el-input
          v-model="queryParams.applyNo"
          placeholder="请输入申请编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请类型" prop="applyType">
        <el-select v-model="queryParams.applyType" placeholder="请选择申请类型" clearable>
          <el-option
            v-for="dict in dict.type.jd_application_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属机构ID" prop="institutionId">
        <el-input
          v-model="queryParams.institutionId"
          placeholder="请输入所属机构ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择申请状态" clearable>
          <el-option
            v-for="dict in dict.type.jd_application_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="提交时间" prop="submitTime">
        <el-date-picker clearable
          v-model="queryParams.submitTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择提交时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['judicial:application:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['judicial:application:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['judicial:application:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['judicial:application:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applicationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请ID" align="center" prop="id" />
      <el-table-column label="申请编号" align="center" prop="applyNo" />
      <el-table-column label="申请类型" align="center" prop="applyType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.jd_application_type" :value="scope.row.applyType"/>
        </template>
      </el-table-column>
      <el-table-column label="申请对象类型" align="center" prop="objectType" />
      <el-table-column label="目标对象ID" align="center" prop="targetId" />
      <el-table-column label="所属机构ID" align="center" prop="institutionId" />
      <el-table-column label="申请状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.jd_application_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" align="center" prop="submitTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核人ID" align="center" prop="reviewerId" />
      <el-table-column label="审核时间" align="center" prop="reviewTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" align="center" prop="reviewOpinion" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['judicial:application:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.status === 'DRAFT' || scope.row.status === 'REJECTED'"
            size="mini"
            type="text"
            icon="el-icon-position"
            @click="handleSubmitApplication(scope.row)"
            v-hasPermi="['judicial:application:edit']"
          >提交</el-button>
          <el-button
            v-if="scope.row.status === 'SUBMITTED'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleApproveApplication(scope.row)"
            v-hasPermi="['judicial:application:edit']"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.status === 'SUBMITTED'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleRejectApplication(scope.row)"
            v-hasPermi="['judicial:application:edit']"
          >审核驳回</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['judicial:application:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改申请审批对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="申请编号" prop="applyNo">
              <el-input v-model="form.applyNo" placeholder="请输入申请编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请类型" prop="applyType">
              <el-select v-model="form.applyType" placeholder="请选择申请类型">
                <el-option
                  v-for="dict in dict.type.jd_application_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="目标对象ID" prop="targetId">
              <el-input v-model="form.targetId" placeholder="请输入目标对象ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请人ID" prop="applicantId">
              <el-input v-model="form.applicantId" placeholder="请输入申请人ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属机构ID" prop="institutionId">
              <el-input v-model="form.institutionId" placeholder="请输入所属机构ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="变更前数据JSON" prop="beforeData">
              <el-input v-model="form.beforeData" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="变更后数据JSON" prop="afterData">
              <el-input v-model="form.afterData" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请材料说明" prop="materialDesc">
              <el-input v-model="form.materialDesc" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="审核意见" prop="reviewOpinion">
              <el-input v-model="form.reviewOpinion" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listApplication, getApplication, delApplication, addApplication, updateApplication, submitApplication, approveApplication, rejectApplication } from "@/api/judicial/application"

export default {
  name: "Application",
  dicts: ['jd_application_status', 'jd_application_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 申请审批表格数据
      applicationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyNo: null,
        applyType: null,
        objectType: null,
        institutionId: null,
        status: null,
        submitTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        applyNo: [
          { required: true, message: "申请编号不能为空", trigger: "blur" }
        ],
        applyType: [
          { required: true, message: "申请类型不能为空", trigger: "change" }
        ],
        objectType: [
          { required: true, message: "申请对象类型不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询申请审批列表 */
    getList() {
      this.loading = true
      listApplication(this.queryParams).then(response => {
        this.applicationList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        applyNo: null,
        applyType: null,
        objectType: null,
        targetId: null,
        applicantId: null,
        institutionId: null,
        beforeData: null,
        afterData: null,
        materialDesc: null,
        status: null,
        submitTime: null,
        reviewerId: null,
        reviewTime: null,
        reviewOpinion: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        delFlag: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加申请审批"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getApplication(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改申请审批"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateApplication(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addApplication(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除申请审批编号为"' + ids + '"的数据项？').then(function() {
        return delApplication(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 提交申请操作 */
    handleSubmitApplication(row) {
      this.$modal.confirm('是否确认提交申请编号为"' + row.applyNo + '"的数据项？').then(function() {
        return submitApplication(row.id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("提交成功")
      }).catch(() => {})
    },
    /** 审核通过操作 */
    handleApproveApplication(row) {
      this.$prompt('请输入审核意见', '审核通过', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputType: 'textarea'
      }).then(({ value }) => {
        return approveApplication(row.id, { reviewOpinion: value })
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核通过成功")
      }).catch(() => {})
    },
    /** 审核驳回操作 */
    handleRejectApplication(row) {
      this.$prompt('请输入驳回意见', '审核驳回', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputType: 'textarea'
      }).then(({ value }) => {
        return rejectApplication(row.id, { reviewOpinion: value })
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("审核驳回成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('judicial/application/export', {
        ...this.queryParams
      }, `application_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
