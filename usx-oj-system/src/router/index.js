import { createRouter, createWebHistory } from 'vue-router'

import Login from "../views/Login";
import StudentLayout from "../layout/StudentLayout";
import ProblemLayout from "../layout/ProblemLayout";
import ProblemDetail from "../views/OJ/ProblemDetail";
import Problem from "../views/OJ/Problem";
import ProblemList from "../views/OJ/ProblemList";
import TeacherLayout from "../layout/TeacherLayout";
import TeacherHome from "../views/Teacher/TeacherHome";
import StudentHome from "../views/Student/StudentHome";
import TeacherCourse from "../views/Teacher/TeacherCourse";
import StudentList from "../views/Teacher/StudentList";
import ProblemLibrary from "../views/Teacher/ProblemLibrary";
import CourseLayout from "../layout/CourseLayout";
import TeacherProblem from "../components/Teacher/TeacherProblem";
import TeacherScore from "../views/Teacher/TeacherScore";


const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/courseLay',
    name: 'CourseLayout',
    component: CourseLayout,
    redirect: '/courseLay/stuList',
    children:[
      {
        path: 'stuList',
        name: 'StudentList',
        component: StudentList
      },
      {
        path: 'proLib',
        name: 'ProblemLibrary',
        component: ProblemLibrary,
      },
      {
        path: 'pro',
        name: 'TeacherProblem',
        component: TeacherProblem,
      },
      {
        path: 'score',
        name: 'TeacherScore',
        component: TeacherScore,
      },
    ]
  },
  {
    path: '/stuLay',
    name: 'StudentLayout',
    component: StudentLayout,
    redirect: '/stuLay/stuHome',
    children:[
      {
        path: 'stuHome',
        name: 'StudentHome',
        component: StudentHome
      },
      {
        path: 'proList',
        name: 'ProblemList',
        component: ProblemList
      },
    ]
  },

  {
    path: '/proLay',
    name: 'ProblemLayout',
    component: ProblemLayout,
    children: [
      {
        path: 'problem',
        name: 'Problem',
        component: Problem
      },
      {
        path: 'proDel',
        name: 'ProblemDetail',
        component: ProblemDetail
      }
    ]
  },

  {
    path: '/teaLay',
    name: 'TeacherLayout',
    component: TeacherLayout,
    redirect: '/teaLay/teaCourse',
    children:[
      {
        path: 'teaCourse',
        name: 'TeacherCourse',
        component: TeacherCourse
      },
    ]
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
