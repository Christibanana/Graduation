<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属机构ID" prop="institutionId">
        <el-input
          v-model="queryParams.institutionId"
          placeholder="请输入所属机构ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="执业证号" prop="practiceCertNo">
        <el-input
          v-model="queryParams.practiceCertNo"
          placeholder="请输入执业证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="执业证有效期止" prop="certValidTo">
        <el-date-picker clearable
          v-model="queryParams.certValidTo"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择执业证有效期止">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="鉴定人状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择鉴定人状态" clearable>
          <el-option
            v-for="dict in dict.type.jd_appraiser_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['judicial:appraiser:add']"
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
          v-hasPermi="['judicial:appraiser:edit']"
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
          v-hasPermi="['judicial:appraiser:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['judicial:appraiser:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appraiserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="鉴定人ID" align="center" prop="id" />
      <el-table-column label="所属机构ID" align="center" prop="institutionId" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="gender">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender"/>
        </template>
      </el-table-column>
      <el-table-column label="专业技术职称" align="center" prop="professionalTitle" />
      <el-table-column label="执业证号" align="center" prop="practiceCertNo" />
      <el-table-column label="执业类别" align="center" prop="practiceCategory" />
      <el-table-column label="执业证有效期止" align="center" prop="certValidTo" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.certValidTo, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="鉴定人状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.jd_appraiser_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['judicial:appraiser:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['judicial:appraiser:remove']"
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

    <!-- 添加或修改司法鉴定人对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="所属机构ID" prop="institutionId">
              <el-input v-model="form.institutionId" placeholder="请输入所属机构ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="form.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="学历" prop="education">
              <el-input v-model="form.education" placeholder="请输入学历" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="专业技术职称" prop="professionalTitle">
              <el-input v-model="form.professionalTitle" placeholder="请输入专业技术职称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="执业证号" prop="practiceCertNo">
              <el-input v-model="form.practiceCertNo" placeholder="请输入执业证号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="执业证有效期起" prop="certValidFrom">
              <el-date-picker clearable
                v-model="form.certValidFrom"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择执业证有效期起">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="执业证有效期止" prop="certValidTo">
              <el-date-picker clearable
                v-model="form.certValidTo"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择执业证有效期止">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="鉴定人状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择鉴定人状态">
                <el-option
                  v-for="dict in dict.type.jd_appraiser_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="删除标志" prop="delFlag">
              <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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
import { listAppraiser, getAppraiser, delAppraiser, addAppraiser, updateAppraiser } from "@/api/judicial/appraiser"

export default {
  name: "Appraiser",
  dicts: ['jd_appraiser_status', 'sys_user_sex'],
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
      // 司法鉴定人表格数据
      appraiserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        institutionId: null,
        name: null,
        practiceCertNo: null,
        practiceCategory: null,
        certValidTo: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        institutionId: [
          { required: true, message: "所属机构ID不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        practiceCertNo: [
          { required: true, message: "执业证号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询司法鉴定人列表 */
    getList() {
      this.loading = true
      listAppraiser(this.queryParams).then(response => {
        this.appraiserList = response.rows
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
        institutionId: null,
        name: null,
        gender: null,
        idCard: null,
        education: null,
        professionalTitle: null,
        practiceCertNo: null,
        practiceCategory: null,
        phone: null,
        certValidFrom: null,
        certValidTo: null,
        status: null,
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
      this.title = "添加司法鉴定人"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAppraiser(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改司法鉴定人"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAppraiser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAppraiser(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除司法鉴定人编号为"' + ids + '"的数据项？').then(function() {
        return delAppraiser(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('judicial/appraiser/export', {
        ...this.queryParams
      }, `appraiser_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
