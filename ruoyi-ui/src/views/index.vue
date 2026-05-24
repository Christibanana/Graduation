<template>
  <div class="app-container home">
    <section class="platform-hero">
      <div class="hero-content">
        <div class="hero-label">司法行政 · 司法鉴定信息化管理</div>
        <h1>司法鉴定平台</h1>
      </div>
      <div class="hero-panel">
        <div class="panel-title">业务闭环</div>
        <div class="flow-line">
          <span>正式档案</span>
          <i class="el-icon-right"></i>
          <span>申请审批</span>
          <i class="el-icon-right"></i>
          <span>审核生效</span>
          <i class="el-icon-right"></i>
          <span>证照预警</span>
        </div>
      </div>
    </section>

    <section class="overview-grid">
      <article v-for="item in overview" :key="item.title" class="overview-card">
        <div class="card-icon" :class="item.type">
          <i :class="item.icon"></i>
        </div>
        <div>
          <h3>{{ item.title }}</h3>
          <p>{{ item.text }}</p>
        </div>
      </article>
    </section>

    <section class="main-grid">
      <div class="module-section">
        <div class="section-head">
          <h2>业务模块</h2>
          <span>围绕机构、鉴定人、审批和预警形成管理主线</span>
        </div>
        <div class="module-grid">
          <article v-for="module in modules" :key="module.name" class="module-card">
            <div class="module-icon">
              <i :class="module.icon"></i>
            </div>
            <div class="module-body">
              <h3>{{ module.name }}</h3>
              <p>{{ module.desc }}</p>
              <el-button type="text" @click="goRoute(module)">进入模块</el-button>
            </div>
          </article>
        </div>
      </div>

      <aside class="focus-section">
        <div class="section-head compact">
          <h2>演示重点</h2>
          <span>当前版本覆盖的核心场景</span>
        </div>
        <div class="focus-list">
          <div v-for="item in focusList" :key="item" class="focus-item">
            <i class="el-icon-check"></i>
            <span>{{ item }}</span>
          </div>
        </div>
      </aside>
    </section>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      overview: [
        { title: "机构档案", text: "维护司法鉴定机构基本信息、许可证号、所属地区和有效期。", icon: "el-icon-office-building", type: "blue" },
        { title: "鉴定人名册", text: "维护执业证号、执业类别、所属机构和证书有效期。", icon: "el-icon-user", type: "green" },
        { title: "审批生效", text: "通过申请提交、审核通过、审核驳回形成正式档案变更闭环。", icon: "el-icon-finished", type: "gold" },
        { title: "证照预警", text: "按已过期和九十天内到期识别机构许可证与鉴定人执业证。", icon: "el-icon-warning-outline", type: "red" }
      ],
      modules: [
        { name: "司法鉴定机构", desc: "机构正式档案、许可证信息、地区和状态维护。", icon: "el-icon-bank-card", fallbackPath: "/system/institution" },
        { name: "司法鉴定人", desc: "鉴定人正式档案、执业证号、执业类别和所属机构维护。", icon: "el-icon-s-custom", fallbackPath: "/judicial/appraiser" },
        { name: "申请审批", desc: "登记、变更、延续、注销申请的提交与审核。", icon: "el-icon-document-checked", fallbackPath: "/judicial/application" },
        { name: "机构证照预警", desc: "按许可证有效期筛选即将到期与已过期机构。", icon: "el-icon-bell", fallbackPath: "/judicial/warning/institution" },
        { name: "鉴定人证照预警", desc: "按执业证有效期筛选即将到期与已过期鉴定人。", icon: "el-icon-alarm-clock", fallbackPath: "/judicial/warning/appraiser" }
      ],
      focusList: [
        "正式档案只在审批通过后生效",
        "申请状态覆盖草稿、待审核、审核通过、审核驳回",
        "证照预警只查询正式档案，不自动修改状态",
        "鉴定人预警关联展示所属机构名称",
        "名册查询角色可通过菜单权限控制查看范围"
      ]
    }
  },
  methods: {
    goRoute(module) {
      const targetPath = this.findMenuPathByTitle(module.name) || module.fallbackPath
      const resolved = this.$router.resolve(targetPath)
      if (!resolved.route.matched.length) {
        this.$message.warning("当前账号未加载该菜单，请检查角色菜单权限")
        return
      }
      this.$router.push(targetPath).catch(() => {})
    },
    findMenuPathByTitle(title) {
      const routes = this.$store.getters.sidebarRouters || []
      const normalizePath = (basePath, routePath) => {
        if (!routePath) {
          return basePath || "/"
        }
        if (/^(https?:|mailto:|tel:)/.test(routePath)) {
          return routePath
        }
        if (routePath.charAt(0) === "/") {
          return routePath
        }
        const base = basePath && basePath !== "/" ? basePath.replace(/\/$/, "") : ""
        return `${base}/${routePath}`.replace(/\/+/g, "/")
      }
      const walk = (items, basePath = "") => {
        for (const item of items) {
          if (!item || item.hidden) {
            continue
          }
          const currentPath = normalizePath(basePath, item.path)
          if (item.meta && item.meta.title === title && !item.children) {
            return currentPath
          }
          if (item.children && item.children.length) {
            const found = walk(item.children, currentPath)
            if (found) {
              return found
            }
          }
          if (item.meta && item.meta.title === title) {
            return currentPath
          }
        }
        return ""
      }
      return walk(routes)
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  min-height: calc(100vh - 84px);
  padding: 22px;
  background: #f4f7fb;
  color: #1f2d3d;
}

.platform-hero {
  position: relative;
  display: grid;
  grid-template-columns: minmax(0, 1fr) 430px;
  gap: 24px;
  min-height: 260px;
  padding: 38px;
  border-radius: 8px;
  overflow: hidden;
  background:
    linear-gradient(135deg, rgba(12, 58, 111, 0.96), rgba(10, 112, 137, 0.9)),
    linear-gradient(90deg, rgba(255, 255, 255, 0.08) 1px, transparent 1px),
    linear-gradient(0deg, rgba(255, 255, 255, 0.08) 1px, transparent 1px);
  background-size: auto, 44px 44px, 44px 44px;
  box-shadow: 0 16px 36px rgba(14, 63, 109, 0.18);
}

.hero-content {
  position: relative;
  z-index: 1;
  align-self: center;
  color: #ffffff;
}

.hero-label {
  display: inline-flex;
  align-items: center;
  min-height: 32px;
  padding: 0 14px;
  margin-bottom: 22px;
  border-radius: 4px;
  background: rgba(255, 255, 255, 0.14);
  border: 1px solid rgba(255, 255, 255, 0.22);
  font-size: 14px;
}

.hero-content h1 {
  margin: 0 0 14px;
  font-size: 42px;
  line-height: 1.18;
  font-weight: 700;
}

.college {
  margin: 0 0 8px;
  font-size: 19px;
  line-height: 1.45;
  color: rgba(255, 255, 255, 0.9);
}

.author {
  margin: 0;
  font-size: 15px;
  line-height: 1.5;
  color: rgba(255, 255, 255, 0.78);
}

.hero-panel {
  position: relative;
  z-index: 1;
  align-self: stretch;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 28px;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.13);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: #ffffff;
}

.panel-title {
  margin-bottom: 18px;
  font-size: 18px;
  font-weight: 600;
}

.flow-line {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.flow-line span {
  display: inline-flex;
  align-items: center;
  min-height: 32px;
  padding: 0 12px;
  border-radius: 4px;
  background: rgba(255, 255, 255, 0.14);
  font-size: 14px;
}

.overview-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
  margin-top: 18px;
}

.overview-card,
.module-card,
.focus-section {
  border-radius: 8px;
  background: #ffffff;
  border: 1px solid #e7edf5;
  box-shadow: 0 10px 24px rgba(25, 47, 77, 0.06);
}

.overview-card {
  display: flex;
  min-height: 128px;
  padding: 22px;
}

.card-icon,
.module-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  width: 44px;
  height: 44px;
  border-radius: 8px;
  margin-right: 16px;
  color: #ffffff;
  font-size: 22px;
}

.blue {
  background: #1f6fb2;
}

.green {
  background: #1f8d73;
}

.gold {
  background: #b57916;
}

.red {
  background: #c84a43;
}

.overview-card h3,
.module-card h3,
.section-head h2 {
  margin: 0;
}

.overview-card h3,
.module-card h3 {
  color: #1d2d44;
  font-size: 17px;
  line-height: 1.35;
}

.overview-card p,
.module-card p {
  margin: 8px 0 0;
  color: #65758b;
  line-height: 1.65;
  font-size: 14px;
}

.main-grid {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 360px;
  gap: 18px;
  margin-top: 18px;
}

.module-section {
  min-width: 0;
}

.section-head {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin-bottom: 14px;
}

.section-head h2 {
  color: #1d2d44;
  font-size: 20px;
}

.section-head span {
  color: #7a8798;
  font-size: 13px;
}

.section-head.compact {
  display: block;
  margin-bottom: 18px;
}

.section-head.compact h2 {
  margin-bottom: 8px;
}

.module-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 16px;
}

.module-card {
  display: flex;
  min-height: 154px;
  padding: 22px;
}

.module-icon {
  background: #eef5fb;
  color: #176295;
}

.module-body {
  min-width: 0;
}

.module-body .el-button {
  padding: 10px 0 0;
  color: #176295;
}

.focus-section {
  padding: 22px;
}

.focus-list {
  display: grid;
  gap: 13px;
}

.focus-item {
  display: flex;
  align-items: flex-start;
  min-height: 36px;
  padding-bottom: 12px;
  border-bottom: 1px solid #edf1f6;
  color: #35475d;
  line-height: 1.55;
}

.focus-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.focus-item i {
  margin: 3px 10px 0 0;
  color: #1f8d73;
  font-size: 16px;
}

@media (max-width: 1200px) {
  .platform-hero,
  .main-grid {
    grid-template-columns: 1fr;
  }

  .overview-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 760px) {
  .home {
    padding: 14px;
  }

  .platform-hero {
    padding: 24px;
  }

  .hero-content h1 {
    font-size: 31px;
  }

  .overview-grid,
  .module-grid {
    grid-template-columns: 1fr;
  }

  .section-head {
    display: block;
  }

  .section-head span {
    display: block;
    margin-top: 8px;
  }
}
</style>
