'use client';

import Link from 'next/link';
import { useAuth } from './context/AuthContext';

export default function Home() {
  const { user, logout } = useAuth();

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col items-center justify-center px-4">
      <div className="max-w-md w-full text-center">
        <h1 className="text-5xl font-bold text-gray-900 mb-4">CarryHub</h1>
        <p className="text-xl text-gray-600 mb-12">
          你的超级社区：博客 · 社交 · 电商 · 任务协作
        </p>

        {user ? (
          <div className="space-y-6">
            <p className="text-2xl">
              欢迎回来，<span className="font-semibold text-blue-600">{user.nickname || user.username}</span>！
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Link
                href="/dashboard"
                className="px-8 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
              >
                进入个人中心
              </Link>
              <button
                onClick={logout}
                className="px-8 py-3 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition"
              >
                退出登录
              </button>
            </div>
          </div>
        ) : (
          <div className="space-y-4">
            <p className="text-lg text-gray-700 mb-8">
              加入我们，一起构建属于自己的社区
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Link
                href="/login"
                className="px-8 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
              >
                登录
              </Link>
              <Link
                href="/register"
                className="px-8 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition"
              >
                立即注册
              </Link>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}