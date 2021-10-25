<template>

  <!--    功能键-->
  <div style="margin: 20px 0;">
    <!--        选择器-->
    <el-select
        name="proList"
        v-model="selValue"
        placeholder="请选择题集"
        @change="selectChanged">
      <el-option label="全部" value=""></el-option>
      <el-option
          v-for="item in options"
          :key="item.uuid"
          :label="item.name"
          :value="item.uuid">
      </el-option>
    </el-select>
    <el-select
        name="proList"
        v-model="selValue3"
        placeholder="请选择题型"
        style="margin-left: 20px"
        @change="selectChanged3">
      <el-option label="选择题" value="选择题"></el-option>
      <el-option label="程序题" value="程序题"></el-option>
    </el-select>
    <!--        搜索框-->
    <el-input placeholder="请输入试题名称"
              style="width: 300px;margin-left: 20px"
              v-model="search"
              type="text"
              clearable>
      <template #append>
        <el-button icon="el-icon-search" @click="forSearch"></el-button>
      </template>
    </el-input>

    <div style="float: right;">
      <el-button type="success"
                 @click="addPro"
                 class="el-icon-circle-plus">
        从题库中添加
      </el-button>

      <el-button type="primary"
                 @click="createPro"
                 class="el-icon-circle-plus">
        新建试题
      </el-button>
    </div>

    <!--从题库中选择-->
    <el-dialog title="添加题目" v-model="dialogFormVisible4" width="50%">
      <el-select
          name="proList"
          v-model="selValue3"
          placeholder="请选择题型"
          @change="selectChanged3">
        <el-option label="选择题" value="选择题"></el-option>
        <el-option label="程序题" value="程序题"></el-option>
      </el-select>

      <!--        搜索框-->
      <el-input placeholder="请输入试题名称"
                style="width: 300px;margin-left: 20px"
                v-model="search2"
                type="text"
                clearable>
        <template #append>
          <el-button icon="el-icon-search" @click="showPro"></el-button>
        </template>
      </el-input>

      <div style="float: right;margin-bottom: 10px">
        <el-select
            name="proList"
            v-model="selValue"
            placeholder="请选择题集"
            @change="selectChanged2">
          <el-option
              v-for="item in options"
              :key="item.uuid"
              :label="item.name"
              :value="item.uuid">
          </el-option>
        </el-select>
      </div>

      <el-table
          :data="tableData2"
          border
          style="width: 100%"
          :cell-style="cellStyle"
          @cell-click="showProDetail">
        <el-table-column
            prop="id"
            label="编号"
            width="150"
            sortable>
        </el-table-column>
        <el-table-column
            prop="title"
            label="标题">
        </el-table-column>
        <el-table-column
            prop="type"
            label="题型"
            width="150">
        </el-table-column>
        <el-table-column
            prop="mark"
            label="分值"
            width="150">
        </el-table-column>
        <el-table-column
            label="操作"
            width="100">
          <template #default="scope">
            <el-button type="primary" size="mini" @click="addToProList(scope.row.uuid)">添加</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--    分页控件-->
      <div style="float: left;">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage2"
            :page-sizes="[10,20]"
            :page-size="pageSize2"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total2">
        </el-pagination>
      </div>
    </el-dialog>

    <!--选择题型-->
    <el-dialog title="请选择题型" v-model="dialogFormVisible1" width="500px">
      <div>
        <el-button @click="createSelect">选择题</el-button>
        <el-button @click="createProgram">程序题</el-button>
      </div>
    </el-dialog>

    <!-- 创建选择题-->
    <el-dialog title="新建选择题" v-model="dialogFormVisible2" width="30%">
      <el-form :model="selProForm" status-icon :rules="rules" ref="selProForm" label-position="left">
        <el-form-item label="题目" prop="title" style="width: 80%">
          <el-input v-model="selProForm.title" type="textarea" :rows="2" placeholder="在此填写题目内容"></el-input>
        </el-form-item>
        <el-form-item label="选项A" prop="optionA" style="width: 80%">
          <el-input v-model="selProForm.optionA" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="选项B" prop="optionB" style="width: 80%">
          <el-input v-model="selProForm.optionB" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="选项C" prop="optionC" style="width: 80%">
          <el-input v-model="selProForm.optionC" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="选项D" prop="optionD" style="width: 80%">
          <el-input v-model="selProForm.optionD" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="答案" prop="answer" style="width: 80%">
          <el-radio v-model="selProForm.answer" label="A">A</el-radio>
          <el-radio v-model="selProForm.answer" label="B">B</el-radio>
          <el-radio v-model="selProForm.answer" label="C">C</el-radio>
          <el-radio v-model="selProForm.answer" label="D">D</el-radio>
        </el-form-item>
        <el-form-item label="分值" prop="mark" style="width: 50%">
          <el-input v-model="selProForm.mark" disabled></el-input>
        </el-form-item>
        <el-select
            name="proList"
            v-model="selValue2"
            placeholder="请选择题集"
            @change="selectChanged2">
          <el-option
              v-for="item in options"
              :key="item.uuid"
              :label="item.name"
              :value="item.uuid">
          </el-option>
        </el-select>
        <el-form-item style="margin-top: 20px">
          <el-button type="primary" @click="submitSelProForm('selProForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!--创建程序题-->
    <el-dialog title="新建程序题" v-model="dialogFormVisible3" width="50%" :close-on-click-modal="false">

      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm"
               label-position="top">
        <el-form-item label="标题" prop="title" style="width: 50%">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <div id="div1">
          <h1 align="center">标题</h1>
          <p style="color: red">(该模板内所有内容都可以删除, 且所有红字段落都不应该保留)</p>
          <h2>描述</h2>
          <p style="color: red">在此处编写题目描述信息</p>
          <p style="color: red">点击编辑器菜单栏按钮 >_ 添加示例代码</p>
          <br>
          <h2>输入示例</h2>
          <br>
          <br>
          <h2>输出示例</h2>
          <br>
          <br>
        </div>
        <div>
          <h2>测试用例（按需填写不必全填写）</h2>
          <div>
            <el-form-item label="输入样例1" prop="inputA" style="width: 30%;float: left;margin-right: 70px">
              <el-input v-model="ruleForm.inputA" type="textarea" :rows="2"
                        placeholder="请在此处填写输入样例"></el-input>
            </el-form-item>
            <el-form-item label="输出样例1" prop="outputA" style="width: 30%;float: left">
              <el-input v-model="ruleForm.outputA" type="textarea" :rows="2"
                        placeholder="请在此处填写输出样例"></el-input>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="输入样例2" prop="inputB" style="width: 30%;float: left;margin-right: 70px">
              <el-input v-model="ruleForm.inputB" type="textarea" :rows="2"
                        placeholder="请在此处填写输入样例"></el-input>
            </el-form-item>
            <el-form-item label="输出样例2" prop="outputB" style="width: 30%;float: left">
              <el-input v-model="ruleForm.outputB" type="textarea" :rows="2"
                        placeholder="请在此处填写输出样例"></el-input>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="输入样例3" prop="inputC" style="width: 30%;float: left;margin-right: 70px">
              <el-input v-model="ruleForm.inputC" type="textarea" :rows="2"
                        placeholder="请在此处填写输入样例"></el-input>
            </el-form-item>
            <el-form-item label="输出样例3" prop="outputC" style="width: 30%;float: left">
              <el-input v-model="ruleForm.outputC" type="textarea" :rows="2"
                        placeholder="请在此处填写输出样例"></el-input>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="输入样例4" prop="inputD" style="width: 30%;float: left;margin-right: 70px">
              <el-input v-model="ruleForm.inputD" type="textarea" :rows="2"
                        placeholder="请在此处填写输入样例"></el-input>
            </el-form-item>
            <el-form-item label="输出样例4" prop="outputD" style="width: 30%;float: left">
              <el-input v-model="ruleForm.outputD" type="textarea" :rows="2"
                        placeholder="请在此处填写输出样例"></el-input>
            </el-form-item>
          </div>
          <div>
            <el-form-item label="输入样例5" prop="inputE" style="width: 30%;float: left;margin-right: 70px">
              <el-input v-model="ruleForm.inputE" type="textarea" :rows="2"
                        placeholder="请在此处填写输入样例"></el-input>
            </el-form-item>
            <el-form-item label="输出样例5" prop="outputE" style="width: 30%;float: left">
              <el-input v-model="ruleForm.outputE" type="textarea" :rows="2"
                        placeholder="请在此处填写输出样例"></el-input>
            </el-form-item>
          </div>
        </div>
        <el-form-item label="分值" prop="mark" style="width: 50%">
          <el-input v-model="ruleForm.mark"></el-input>
        </el-form-item>
        <el-select
            name="proList"
            v-model="selValue2"
            placeholder="请选择题集"
            @change="selectChanged2">
          <el-option
              v-for="item in options"
              :key="item.uuid"
              :label="item.name"
              :value="item.uuid">
          </el-option>
        </el-select>
        <!--                <el-form-item label="内容" prop="content" >-->
        <!--                    <tinymce-editor :init="init" v-model="ruleForm.content"></tinymce-editor>-->
        <!--                </el-form-item>-->
        <!--                <el-form-item label="输入格式" prop="input" >-->
        <!--                    <el-input-->
        <!--                            type="textarea"-->
        <!--                            autosize-->
        <!--                            placeholder="请输入内容"-->
        <!--                            v-model="ruleForm.input">-->
        <!--                    </el-input>-->
        <!--                </el-form-item>-->
        <!--                <el-form-item label="输出格式" prop="output" >-->
        <!--                    <el-input-->
        <!--                            type="textarea"-->
        <!--                            autosize-->
        <!--                            placeholder="请输入内容"-->
        <!--                            v-model="ruleForm.output">-->
        <!--                    </el-input>-->
        <!--                </el-form-item>-->
        <!--                <div>-->
        <!--                    <el-row :gutter="10">-->
        <!--                        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="12"><div class="grid-content bg-purple-light">-->
        <!--                            <el-form-item label="输入样例1" prop="inputExample1" style="margin-bottom: 0px">-->
        <!--                                <el-input-->
        <!--                                        type="textarea"-->
        <!--                                        placeholder="请输入内容"-->
        <!--                                        style="height: 80px"-->
        <!--                                        v-model="ruleForm.inputExample1">-->
        <!--                                </el-input>-->
        <!--                            </el-form-item>-->
        <!--                        </div></el-col>-->
        <!--                        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="12"><div class="grid-content bg-purple">-->
        <!--                            <el-form-item label="输出样例1" prop="outputExample1" style="margin-bottom: 0px">-->
        <!--                                <el-input-->
        <!--                                        type="textarea"-->
        <!--                                        placeholder="请输入内容"-->
        <!--                                        style="height: 80px"-->
        <!--                                        v-model="ruleForm.outputExample1">-->
        <!--                                </el-input>-->
        <!--                            </el-form-item>-->
        <!--                        </div></el-col>-->
        <!--                    </el-row>-->
        <!--                </div>-->
        <!--                <div>-->
        <!--                    <el-row :gutter="10">-->
        <!--                        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="12"><div class="grid-content bg-purple-light">-->
        <!--                            <el-form-item label="输入样例2" prop="inputExample2">-->
        <!--                                <el-input-->
        <!--                                        type="textarea"-->
        <!--                                        placeholder="请输入内容"-->
        <!--                                        style="height: 80px"-->
        <!--                                        v-model="ruleForm.inputExample2">-->
        <!--                                </el-input>-->
        <!--                            </el-form-item>-->
        <!--                        </div></el-col>-->
        <!--                        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="12"><div class="grid-content bg-purple">-->
        <!--                            <el-form-item label="输出样例2" prop="outputExample2" >-->
        <!--                                <el-input-->
        <!--                                        type="textarea"-->
        <!--                                        placeholder="请输入内容"-->
        <!--                                        style="height: 80px"-->
        <!--                                        v-model="ruleForm.outputExample2">-->
        <!--                                </el-input>-->
        <!--                            </el-form-item>-->
        <!--                        </div></el-col>-->
        <!--                    </el-row>-->
        <!--                </div>-->
        <el-form-item style="margin-top: 20px">
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <!-- 编辑选择题-->
    <el-dialog title="编辑选择题" v-model="dialogFormVisible6" width="30%">
      <el-form :model="selProForm" status-icon :rules="rules" ref="selProForm" label-position="left">
        <el-form-item label="题目" prop="title" style="width: 80%">
          <el-input v-model="selProForm.title" type="textarea" :rows="2" placeholder="在此填写题目内容"></el-input>
        </el-form-item>
        <el-form-item label="选项A" prop="optionA" style="width: 80%">
          <el-input v-model="selProForm.optionA" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="选项B" prop="optionB" style="width: 80%">
          <el-input v-model="selProForm.optionB" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="选项C" prop="optionC" style="width: 80%">
          <el-input v-model="selProForm.optionC" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="选项D" prop="optionD" style="width: 80%">
          <el-input v-model="selProForm.optionD" type="textarea" :rows="2" placeholder="在此填写选项内容"></el-input>
        </el-form-item>
        <el-form-item label="答案" prop="answer" style="width: 80%">
          <el-radio v-model="selProForm.answer" label="A">A</el-radio>
          <el-radio v-model="selProForm.answer" label="B">B</el-radio>
          <el-radio v-model="selProForm.answer" label="C">C</el-radio>
          <el-radio v-model="selProForm.answer" label="D">D</el-radio>
        </el-form-item>
        <el-form-item label="分值" prop="mark" style="width: 50%">
          <el-input v-model="selProForm.mark" disabled></el-input>
        </el-form-item>
        <el-form-item style="margin-top: 20px">
          <el-button type="primary" @click="updateSelPro('selProForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 查看选择题-->
    <!--        <el-dialog title="题目详情" v-model="dialogFormVisible6" width="30%">-->
    <!--            <div :data="showSelPro">-->
    <!--                <h3 style="text-indent: 2em">{{showSelPro.title}}</h3>-->

    <!--                <div style="margin-top: 20px;margin-left: 20px;">-->
    <!--                    <div style="float: left;width: 200px">-->
    <!--                        <span class="selOption" tabindex="1">A</span>-->
    <!--                        <p>{{showSelPro.optionA}}</p>-->
    <!--                    </div>-->
    <!--                    <div style="float: left;width: 200px">-->
    <!--                        <span class="selOption" tabindex="2">B</span>-->
    <!--                        <p>{{showSelPro.optionB}}</p>-->
    <!--                    </div>-->
    <!--                </div>-->

    <!--                <div style="padding-top: 40px;margin-left: 20px;">-->
    <!--                    <div style="float: left;width: 200px">-->
    <!--                        <span class="selOption" tabindex="3">C</span>-->
    <!--                        <p>{{showSelPro.optionC}}</p>-->
    <!--                    </div>-->
    <!--                    <div style="float: left;width: 200px">-->
    <!--                        <span class="selOption" tabindex="4">D</span>-->
    <!--                        <p>{{showSelPro.optionD}}</p>-->
    <!--                    </div>-->
    <!--                </div>-->

    <!--                <div style="padding-top: 40px;margin-left: 20px;">-->
    <!--                    <el-tag type="success">正确答案：{{showSelPro.answer}}</el-tag>-->
    <!--                </div>-->
    <!--            </div>-->
    <!--        </el-dialog>-->

    <!--编辑程序题-->
    <el-dialog title="编辑程序题" v-model="dialogFormVisible5" width="50%" :close-on-click-modal="false">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="题目" name="first">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm"
                   label-position="top">
            <el-form-item label="标题" prop="title" style="width: 50%">
              <el-input v-model="ruleForm.title"></el-input>
            </el-form-item>
            <div id="div2">
              <h1 align="center">标题</h1>
              <p style="color: red">(该模板内所有内容都可以删除, 且所有红字段落都不应该保留)</p>
              <h2>描述</h2>
              <p style="color: red">在此处编写题目描述信息</p>
              <p style="color: red">点击编辑器菜单栏按钮 >_ 添加示例代码</p>
              <br>
              <h2>输入示例</h2>
              <br>
              <br>
              <h2>输出示例</h2>
              <br>
              <br>
            </div>
            <el-form-item label="分值" prop="mark" style="width: 50%">
              <el-input v-model="ruleForm.mark"></el-input>
            </el-form-item>
            <el-form-item style="margin-top: 20px">
              <el-button type="primary" @click="updatePro('ruleForm')">修改</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="测试用例" name="second">
          <el-form :model="textForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm"
                   label-position="top">
            <div>
              <h2>测试用例（按需填写不必全填写）</h2>
              <div>
                <el-form-item label="输入样例1" prop="inputA"
                              style="width: 30%;float: left;margin-right: 70px">
                  <el-input v-model="textForm.inputA" type="textarea" :rows="2"
                            placeholder="请在此处填写输入样例"></el-input>
                </el-form-item>
                <el-form-item label="输出样例1" prop="outputA" style="width: 30%;float: left">
                  <el-input v-model="textForm.outputA" type="textarea" :rows="2"
                            placeholder="请在此处填写输出样例"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label="输入样例2" prop="inputB"
                              style="width: 30%;float: left;margin-right: 70px">
                  <el-input v-model="textForm.inputB" type="textarea" :rows="2"
                            placeholder="请在此处填写输入样例"></el-input>
                </el-form-item>
                <el-form-item label="输出样例2" prop="outputB" style="width: 30%;float: left">
                  <el-input v-model="textForm.outputB" type="textarea" :rows="2"
                            placeholder="请在此处填写输出样例"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label="输入样例3" prop="inputC"
                              style="width: 30%;float: left;margin-right: 70px">
                  <el-input v-model="textForm.inputC" type="textarea" :rows="2"
                            placeholder="请在此处填写输入样例"></el-input>
                </el-form-item>
                <el-form-item label="输出样例3" prop="outputC" style="width: 30%;float: left">
                  <el-input v-model="textForm.outputC" type="textarea" :rows="2"
                            placeholder="请在此处填写输出样例"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label="输入样例4" prop="inputD"
                              style="width: 30%;float: left;margin-right: 70px">
                  <el-input v-model="textForm.inputD" type="textarea" :rows="2"
                            placeholder="请在此处填写输入样例"></el-input>
                </el-form-item>
                <el-form-item label="输出样例4" prop="outputD" style="width: 30%;float: left">
                  <el-input v-model="textForm.outputD" type="textarea" :rows="2"
                            placeholder="请在此处填写输出样例"></el-input>
                </el-form-item>
              </div>
              <div>
                <el-form-item label="输入样例5" prop="inputE"
                              style="width: 30%;float: left;margin-right: 70px">
                  <el-input v-model="textForm.inputE" type="textarea" :rows="2"
                            placeholder="请在此处填写输入样例"></el-input>
                </el-form-item>
                <el-form-item label="输出样例5" prop="outputE" style="width: 30%;float: left">
                  <el-input v-model="textForm.outputE" type="textarea" :rows="2"
                            placeholder="请在此处填写输出样例"></el-input>
                </el-form-item>
              </div>
            </div>
            <el-form-item style="float:none">
              <el-button type="primary" @click="submitTextForm">提交</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="测试代码" name="third">
          <div style="height: 80vh;border: 1px solid #D7DAE2;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);padding: 20px">
            <CodeMirror/>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>

  <!--    主界面题目展示-->
  <el-table
      :data="tableData"
      border
      style="width: 100%"
      :cell-style="cellStyle"
      @cell-click="showProDetail">
    <el-table-column
        prop="id"
        label="编号"
        width="150"
        sortable>
    </el-table-column>
    <!--        <el-table-column-->
    <!--                prop="date"-->
    <!--                label="创建日期"-->
    <!--                width="150"-->
    <!--                sortable>-->
    <!--        </el-table-column>-->
    <el-table-column
        prop="title"
        label="标题">
    </el-table-column>
    <el-table-column
        prop="type"
        label="题型"
        width="150">
    </el-table-column>
    <el-table-column
        prop="mark"
        label="分值"
        width="150">
    </el-table-column>
    <el-table-column
        label="操作"
        width="150">
      <template #default="scope">
        <el-button type="primary" size="mini" @click="edit(scope.row)">编辑</el-button>
        <el-popconfirm
            title="这是一段内容确定删除吗？" @confirm="handleDel(scope.row.uuid)"
        >
          <template #reference>
            <el-button type="danger" size="mini">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <!--    分页控件-->
  <div style="float: left;margin-top: 20px">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10,20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
// import Editor from "@tinymce/tinymce-vue";
// import 'tinymce/tinymce';
// import 'tinymce/themes/silver/theme';
// import 'tinymce/skins/ui/oxide/skin.min.css';
// import "tinymce/icons/default"; //引入编辑器图标icon，不引入则不显示对应图标
// import "tinymce/plugins/image";
// import "tinymce/plugins/link";
// import "tinymce/plugins/code";
// import "tinymce/plugins/table";
// import "tinymce/plugins/lists";
// import "tinymce/plugins/contextmenu";
// import "tinymce/plugins/wordcount";
// import "tinymce/plugins/colorpicker";
// import "tinymce/plugins/textcolor";
import request from "../../utils/request";
import E from 'wangeditor'
import CodeMirror from "../../components/OJ/CodeMirror";

let editor;

export default {
  name: "TeacherProblem",
  components: {
    CodeMirror
    // "tinymce-editor": Editor
  },
  data() {
    return {
      activeName: 'first',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      search: '',
      currentPage2: 1,
      pageSize2: 10,
      total2: 0,
      search2: '',
      proListName: '',
      proListUuid: '',
      selValue: '',//展示时题集选择器
      selValue2: '',//添加时题集选择器
      selValue3: '程序题',//题型选择器
      // activeName: 'first',
      dialogFormVisible1: false,//题型选择的弹窗
      dialogFormVisible2: false,//创建选择题弹窗
      dialogFormVisible3: false,//创建程序题弹窗
      dialogFormVisible4: false,//添加题目的弹窗
      dialogFormVisible5: false,//编辑程序题弹窗
      dialogFormVisible6: false,//编辑选择题弹窗
      tableData: [{}],
      tableData2: [{}],//展示题库中所有的题目
      options: [{}],
      showSelPro: {},
      selProForm: {
        title: '',
        answer: 'A',
        mark: '5',
      },
      textForm: {},//测试用例展示表单
      ruleForm: {
        title: '',
        type: '',
        content: '',
        mark: ''
        // output:'',
        // input:'',
        // inputExample1:'',
        // outputExample1:'',
        // inputExample2:'',
        // outputExample2:'',
      },
      rules: {
        title: [{required: true, message: '请输入题目名称', trigger: 'change'}],
        mark: [{required: true, message: '请输入分值', trigger: 'change'}],
      },
      // init: {
      //     language_url: "/tinymce/zh_CN.js",
      //     language: "zh_CN",
      //     height: 400,
      //     plugins:"link lists image code table colorpicker textcolor wordcount contextmenu",
      //     toolbar:"bold italic underline strikethrough | fontsizeselect | forecolor backcolor | alignleft aligncenter alignright alignjustify|bullist numlist |outdent indent blockquote | undo redo | link unlink image code | removeformat",
      //     branding: false,
      //     images_upload_handler:(blobInfo, success,failure)=> {
      //         success('data:image/jpeg;base64,' + blobInfo.base64())
      //     }
      // },
    };
  },
  created() {
    sessionStorage.removeItem("currentProListUuid");
    let proListUuidJson = sessionStorage.getItem("proListUuid") || "{}";
    this.proListUuid = proListUuidJson;
    this.proListName = sessionStorage.getItem("proListName") || "{}";
    sessionStorage.removeItem("proListUuid");
    sessionStorage.removeItem("proListName");
    // console.log(this.proListUuid)
    if (this.proListUuid != '{}') {
      this.load(this.proListUuid);
    } else {
      this.proListUuid = "";
      this.load("");
    }

    this.loadOption();

  },
  methods: {
    //点击标签页触发事件，加载测试用例数据
    handleClick(tab, event) {
      // console.log(tab, event);
    },

    //提交修改的测试用例
    submitTextForm() {
      request.post("/pro/updateText", this.textForm).then(res => {
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    },

    //加载测试用例数据
    textLoad(uuid) {
      request.get("/pro/text/" + uuid).then(res => {
        console.log(res)
        this.textForm = res.data;
      })
    },

    //提交修改选择题的表单
    updateSelPro(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/selPro/update", this.selProForm).then(res => {
            if (res.code === '0') {
              this.$message({
                type: 'success',
                message: '修改成功'
              })
              this.dialogFormVisible6 = false;
              this.loadSel(this.proListUuid);
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //提交修改程序题的表单
    updatePro(formName) {
      this.ruleForm.content = editor.txt.html();//获取到富文本框里的内容
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/pro/update", this.ruleForm).then(res => {
            if (res.code === '0') {
              this.$message({
                type: 'success',
                message: '修改成功'
              })
              this.dialogFormVisible5 = false;
              this.load(this.proListUuid);
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //打开详情页面
    edit(row) {
      if (this.selValue3 === '程序题') {
        this.activeName = "first";
        this.dialogFormVisible5 = true;
        this.resetForm("ruleForm");
        //关联弹窗里的div，new一个editor对象
        this.$nextTick(() => {
          editor = new E('#div2');
          editor.create();
        })
        request.get("/pro/" + row.id).then(res => {
          this.ruleForm = res.data;
          editor.txt.html(res.data.content);
        })
        this.textLoad(row.uuid);
      } else if (this.selValue3 === '选择题') {
        this.dialogFormVisible6 = true;
        request.get("/selPro/" + row.id).then(res => {
          this.selProForm = res.data;
        })
      }
    },

    //将题目添加到对应题集
    addToProList(uuid) {
      // console.log(uuid);//对应题目的uuid
      // console.log(this.proListUuid);
      if (this.selValue3 === '程序题') {
        request.post("/pro/add", {
          problemUuid: uuid,
          problemListUuid: this.proListUuid
        }).then(res => {
          if (res.code === '0') {
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.load(this.proListUuid)
          } else {
            this.$message({
              type: 'error',
              message: res.msg
            })
          }
        })

      } else if (this.selValue3 === '选择题') {
        request.post("/selPro/add", {
          selProblemUuid: uuid,
          problemListUuid: this.proListUuid
        }).then(res => {
          if (res.code === '0') {
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.loadSel(this.proListUuid)
          } else {
            this.$message({
              type: 'error',
              message: res.msg
            })
          }
        })
      }
    },

    //展示界面的题目搜索功能
    forSearch() {
      if (this.selValue3 === '程序题') {
        this.load(this.proListUuid);
      } else if (this.selValue3 === '选择题') {
        this.loadSel(this.proListUuid);
      }
    },

    //从该题集中删除题目
    handleDel(uuid) {
      // console.log(uuid)
      if (this.selValue3 === '程序题') {
        request.post("/pro/del", {
          problemUuid: uuid,
          problemListUuid: this.proListUuid
        }).then(res => {
          if (res.code === '0') {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            this.load(this.proListUuid);
          } else {
            this.$message({
              type: 'error',
              message: res.msg
            })
          }
        })
      } else if (this.selValue3 === '选择题') {
        request.post("/selPro/del", {
          selProblemUuid: uuid,
          problemListUuid: this.proListUuid
        }).then(res => {
          if (res.code === '0') {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            this.loadSel(this.proListUuid);
          } else {
            this.$message({
              type: 'error',
              message: res.msg
            })
          }
        })
      }

    },

    //下拉框选择时，加载选项相关数据
    selectChanged(val) {
      // console.log(val)
      if (this.selValue3 === '程序题') {
        this.load(val);

      } else if (this.selValue3 === '选择题') {
        this.loadSel(val);
      }
      sessionStorage.setItem("currentProListUuid", val);

      //当选项为 ‘全部’ 时，值value为空，删除currentProListUuid的session
      if (this.selValue === '') {
        sessionStorage.removeItem("currentProListUuid");
      }

      //当选项改变时，设置当前的proListUuid用于搜索时的uuid参数
      this.proListUuid = sessionStorage.getItem("currentProListUuid") || "{}";
      if (this.proListUuid === '{}') {
        this.proListUuid = "";
      }
      // console.log(this.proListUuid)
    },

    //创建时的下拉框选择时，加载选项相关数据
    selectChanged2(val) {
      // console.log(val)
      this.load(val);
      sessionStorage.setItem("currentProListUuid", val);

      //当选项改变时，设置当前的proListUuid用于搜索时的uuid参数
      this.proListUuid = sessionStorage.getItem("currentProListUuid") || "{}";
      if (this.proListUuid === '{}') {
        this.proListUuid = "";
      }
      // console.log(this.proListUuid)
    },

    //添加时题型的下拉框选择时，加载选项相关数据
    selectChanged3(val) {
      if (this.selValue3 === '选择题') {
        this.loadAllSelect();
        this.loadSel(this.proListUuid);
      } else if (this.selValue3 === '程序题') {
        this.loadAllProgram();
        this.load(this.proListUuid);
      }
    },

    //加载页面的程序题
    load(uuid) {
      // console.log(uuid)
      // if (uuid){
      request.get("/pro", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          problemListUuid: uuid,
          courseUuid: sessionStorage.getItem("courseUuid")
        }
      }).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.currentPage = res.data.current;
      })
      // }else {
      //     request.get("/pro/all",{
      //         params:{
      //             pageNum: this.currentPage,
      //             pageSize: this.pageSize,
      //             search: this.search
      //         }
      //     }).then(res => {
      //         this.tableData = res.data.records;
      //         this.total = res.data.total;
      //         this.currentPage = res.data.current;
      //     })
      // }
    },

    //加载页面的选择题
    loadSel(uuid) {
      request.get("/selPro", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          problemListUuid: uuid,
          courseUuid: sessionStorage.getItem("courseUuid")
        }
      }).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.currentPage = res.data.current;
      })
    },

    //加载下拉框里的选项
    loadOption() {
      request.get("/proList", {
        params: {
          search: '',
          courseUuid: sessionStorage.getItem("courseUuid")
        }
      }).then(res => {
        this.options = res.data;
      })

      //当从题集跳转来的时候，初始化选择内容
      if (this.proListName != '{}') {
        this.selValue = this.proListName;
      }
    },

    // cellStyle({row, column, rowIndex, columnIndex}) {
    //     if (columnIndex === 1) {
    //         return "cursor:pointer;padding:20px 0;color:rgb(18,157,250);"
    //     }
    // },


    // showProDetail(row, column, event, cell) {
    //     console.log(row);
    //     if (column.property === "title") {
    //         this.$router.push("/proLay/problem")
    //     }
    // },

    //打开题型选择的弹窗界面
    createPro() {
      this.dialogFormVisible1 = true;
    },

    //打开创建选择题的弹窗界面
    createSelect() {
      this.dialogFormVisible1 = false;
      this.dialogFormVisible2 = true;
      this.selProForm.type = '选择题';
      this.resetForm("selProForm");
    },

    //打开创建程序题的弹窗界面
    createProgram() {
      this.dialogFormVisible1 = false;
      this.dialogFormVisible3 = true;
      this.ruleForm.type = '程序题';
      this.resetForm("ruleForm");
      //关联弹窗里的div，new一个editor对象
      this.$nextTick(() => {
        editor = new E('#div1');
        editor.create();
      })

    },

    //打开从题库添加的弹窗界面
    addPro() {
      this.dialogFormVisible4 = true;
      if (this.selValue3 === '选择题') {
        this.loadAllSelect();
      } else if (this.selValue3 === '程序题') {
        this.loadAllProgram();
      }
    },

    //加载所有编程题
    loadAllProgram() {
      request.get("/pro/all", {
        params: {
          pageNum: this.currentPage2,
          pageSize: this.pageSize2,
          search: this.search2
        }
      }).then(res => {
        this.tableData2 = res.data.records;
        this.total2 = res.data.total;
        this.currentPage2 = res.data.current;
      })
    },

    //加载所有选择题
    loadAllSelect() {
      request.get("/selPro/all", {
        params: {
          pageNum: this.currentPage2,
          pageSize: this.pageSize2,
          search: this.search2
        }
      }).then(res => {
        this.tableData2 = res.data.records;
        this.total2 = res.data.total;
        this.currentPage2 = res.data.current;
      })
    },

    //添加页面的搜索按钮事件
    showPro() {
      if (this.selValue3 === '选择题') {
        this.loadAllSelect();
      } else if (this.selValue3 === '程序题') {
        this.loadAllProgram();
      }
    },

    //提交创建选择题的表单
    submitSelProForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/selPro", {
            title: this.selProForm.title,
            type: this.selProForm.type,
            optionA: this.selProForm.optionA,
            optionB: this.selProForm.optionB,
            optionC: this.selProForm.optionC,
            optionD: this.selProForm.optionD,
            mark: this.selProForm.mark,
            answer: this.selProForm.answer,
            problemListUuid: this.proListUuid
          }).then(res => {
            if (res.code === '0') {
              this.$message({
                type: 'success',
                message: '创建成功'
              })
              this.dialogFormVisible2 = false;
              this.loadSel(this.proListUuid);
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //提交创建程序题的表单
    submitForm(formName) {
      // console.log(editor.txt.html())
      this.ruleForm.content = editor.txt.html();//获取到富文本框里的内容
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("/pro", {
            title: this.ruleForm.title,
            type: this.ruleForm.type,
            content: this.ruleForm.content,
            mark: this.ruleForm.mark,
            problemListUuid: this.proListUuid,
            inputA: this.ruleForm.inputA,
            outputA: this.ruleForm.outputA,
            inputB: this.ruleForm.inputB,
            outputB: this.ruleForm.outputB,
            inputC: this.ruleForm.inputC,
            outputC: this.ruleForm.outputC,
            inputD: this.ruleForm.inputD,
            outputD: this.ruleForm.outputD,
            inputE: this.ruleForm.inputE,
            outputE: this.ruleForm.outputE,
          }).then(res => {
            if (res.code === '0') {
              this.$message({
                type: 'success',
                message: '创建成功'
              })
              this.dialogFormVisible3 = false;
              this.load(this.proListUuid);
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //重置表单内容
    resetForm(formName) {
      this.$nextTick(() => {
        this.$refs[formName].resetFields();
      })

    },

    //分页控件属性
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.load(this.proListUuid);
    },

    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.load(this.proListUuid);
    },
  }
}
</script>

<style scoped>
.selOption {
  height: 30px;
  width: 30px;
  line-height: 30px;
  text-align: center;
  background: #dcdcdc;
  display: inline-block;
  margin-right: 10px;
  font-weight: bolder;
  float: left;
  border-radius: 50%;
}

.selOption:hover {
  background: skyblue;
  cursor: pointer;
}

.selOption:focus {
  background: skyblue;
}

p {
  line-height: 30px;
}
</style>