'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '../context/AuthContext';

export default function Dashboard() {
  const { user, isLoading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!isLoading && !user) {
      router.push('/login');
    }
  }, [user, isLoading, router]);

  if (isLoading) return <div className="min-h-screen flex items-center justify-center">加载中...</div>;
  if (!user) return null;

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center px-4">
      <div className="max-w-2xl w-full bg-white rounded-xl shadow-lg p-10">
        <h1 className="text-4xl font-bold text-center mb-10">个人中心</h1>

        <div className="space-y-6 text-lg">
          <div className="flex items-center space-x-4">
            <span className="font-medium text-gray-700 w-24">头像：</span>
            {user.avatarUrl ? (
              <img src={user.avatarUrl} alt="avatar" className="w-20 h-20 rounded-full" />
            ) : (
              <div className="w-20 h-20 bg-gray-300 rounded-full flex items-center justify-center text-2xl font-bold text-gray-600">
                {user.username[0].toUpperCase()}
              </div>
            )}
          </div>

          <div><span className="font-medium text-gray-700">用户名：</span> {user.username}</div>
          <div><span className="font-medium text-gray-700">昵称：</span> {user.nickname || '未设置'}</div>
          <div><span className="font-medium text-gray-700">用户ID：</span> {user.id}</div>
        </div>

        <div className="mt-10 text-center">
          <p className="text-xl mb-6">
            欢迎，<span className="font-bold text-blue-600">{user.nickname || user.username}</span>！
          </p>
          <p className="text-gray-600">CarryHub MVP v0.1 已就位，更多功能正在开发中...</p>
        </div>
      </div>
    </div>
  );
}