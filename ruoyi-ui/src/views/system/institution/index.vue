<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="机构ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入机构ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机构名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入机构名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="许可证号" prop="licenseNo">
        <el-input
          v-model="queryParams.licenseNo"
          placeholder="请输入许可证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机构负责人" prop="principal">
        <el-input
          v-model="queryParams.principal"
          placeholder="请输入机构负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属地区" prop="region">
        <el-select v-model="queryParams.region" placeholder="请选择所属地区" clearable>
          <el-option
            v-for="dict in dict.type.jd_region"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务范围" prop="businessScope">
        <el-input
          v-model="queryParams.businessScope"
          placeholder="请输入业务范围"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="许可证有效期止" prop="licenseValidTo">
        <el-date-picker clearable
          v-model="queryParams.licenseValidTo"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择许可证有效期止">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="机构状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择机构状态" clearable>
          <el-option
            v-for="dict in dict.type.jd_institution_status"
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
          v-hasPermi="['system:institution:add']"
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
          v-hasPermi="['system:institution:edit']"
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
          v-hasPermi="['system:institution:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:institution:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="institutionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="机构ID" align="center" prop="id" />
      <el-table-column label="机构名称" align="center" prop="name" />
      <el-table-column label="许可证号" align="center" prop="licenseNo" />
      <el-table-column label="统一社会信用代码" align="center" prop="creditCode" />
      <el-table-column label="法定代表人" align="center" prop="legalRepresentative" />
      <el-table-column label="机构负责人" align="center" prop="principal" />
      <el-table-column label="所属地区" align="center" prop="region">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.jd_region" :value="scope.row.region"/>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="业务范围" align="center" prop="businessScope" />
      <el-table-column label="许可证有效期止" align="center" prop="licenseValidTo" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.licenseValidTo, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="机构状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.jd_institution_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:institution:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:institution:remove']"
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

    <!-- 添加或修改司法鉴定机构对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="机构名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入机构名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="许可证号" prop="licenseNo">
              <el-input v-model="form.licenseNo" placeholder="请输入许可证号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="统一社会信用代码" prop="creditCode">
              <el-input v-model="form.creditCode" placeholder="请输入统一社会信用代码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="法定代表人" prop="legalRepresentative">
              <el-input v-model="form.legalRepresentative" placeholder="请输入法定代表人" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="机构负责人" prop="principal">
              <el-input v-model="form.principal" placeholder="请输入机构负责人" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属地区" prop="region">
              <el-select v-model="form.region" placeholder="请选择所属地区">
                <el-option
                  v-for="dict in dict.type.jd_region"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="住所" prop="address">
              <el-input v-model="form.address" placeholder="请输入住所" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="业务范围" prop="businessScope">
              <el-input v-model="form.businessScope" placeholder="请输入业务范围" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="许可证有效期起" prop="licenseValidFrom">
              <el-date-picker clearable
                v-model="form.licenseValidFrom"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择许可证有效期起">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="许可证有效期止" prop="licenseValidTo">
              <el-date-picker clearable
                v-model="form.licenseValidTo"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择许可证有效期止">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="机构状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择机构状态">
                <el-option
                  v-for="dict in dict.type.jd_institution_status"
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
import { listInstitution, getInstitution, delInstitution, addInstitution, updateInstitution } from "@/api/system/institution"

export default {
  name: "Institution",
  dicts: ['jd_region', 'jd_institution_status'],
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
      // 司法鉴定机构表格数据
      institutionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        name: null,
        licenseNo: null,
        principal: null,
        region: null,
        businessScope: null,
        licenseValidTo: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "机构名称不能为空", trigger: "blur" }
        ],
        licenseNo: [
          { required: true, message: "许可证号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询司法鉴定机构列表 */
    getList() {
      this.loading = true
      listInstitution(this.queryParams).then(response => {
        this.institutionList = response.rows
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
        name: null,
        licenseNo: null,
        creditCode: null,
        legalRepresentative: null,
        principal: null,
        region: null,
        address: null,
        phone: null,
        businessScope: null,
        licenseValidFrom: null,
        licenseValidTo: null,
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
      this.title = "添加司法鉴定机构"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getInstitution(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改司法鉴定机构"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInstitution(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addInstitution(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除司法鉴定机构编号为"' + ids + '"的数据项？').then(function() {
        return delInstitution(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/institution/export', {
        ...this.queryParams
      }, `institution_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
