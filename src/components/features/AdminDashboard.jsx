import { useState } from 'react';
import { 
  Users, 
  Shield, 
  FileText, 
  Settings, 
  BookOpen, 
  Archive, 
  Monitor, 
  BarChart2, 
  Bell, 
  HelpCircle,
  User,
  Lock,
  LogOut,
  Menu,
  X,
  AlertTriangle,
  CheckCircle,
  Search,
  Home
} from 'lucide-react';

export default function AdminDashboard() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [notificationCount] = useState(3);

  const handleTabChange = (tab) => {
    setActiveTab(tab);
    if (window.innerWidth < 768) {
      setSidebarOpen(false);
    }
  };

  // Render content based on active tab
  const renderContent = () => {
    switch(activeTab) {
      case 'dashboard':
        return <DashboardContent />;
      case 'users':
        return <UserManagement />;
      case 'permissions':
        return <PermissionManagement />;
      case 'logs':
        return <UserLogs />;
      case 'settings':
        return <SystemSettings />;
      case 'categories':
        return <CategoryManagement />;
      case 'backup':
        return <BackupRestore />;
      case 'reports':
        return <AttendanceReports />;
      case 'support':
        return <TechnicalSupport />;
      default:
        return <DashboardContent />;
    }
  };

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Mobile sidebar toggle */}
      <div className="md:hidden fixed top-0 left-0 z-40 p-4">
        <button onClick={() => setSidebarOpen(!sidebarOpen)} className="text-gray-600 focus:outline-none">
          {sidebarOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
      </div>

      {/* Sidebar */}
      <div className={`fixed inset-y-0 left-0 w-64 bg-indigo-800 text-white transform transition-transform duration-300 ease-in-out z-30 ${sidebarOpen ? 'translate-x-0' : '-translate-x-full md:translate-x-0'}`}>
        <div className="p-4">
          <div className="flex items-center mb-8">
            <img src="/api/placeholder/40/40" alt="logo" className="w-10 h-10 rounded-full mr-3" />
            <div>
              <h2 className="font-bold text-lg">Phòng Đào Tạo</h2>
              <h3 className="text-sm font-medium">Đại học Công nghệ Sài Gòn</h3>
            </div>
          </div>

          <nav className="space-y-1">
            <button 
              onClick={() => handleTabChange('dashboard')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'dashboard' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <Home size={18} className="mr-3" />
              <span>Trang chủ</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('users')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'users' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <Users size={18} className="mr-3" />
              <span>Quản lý người dùng</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('permissions')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'permissions' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <Shield size={18} className="mr-3" />
              <span>Phân quyền</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('logs')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'logs' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <FileText size={18} className="mr-3" />
              <span>Nhật ký hoạt động</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('settings')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'settings' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <Settings size={18} className="mr-3" />
              <span>Cài đặt hệ thống</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('categories')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'categories' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <BookOpen size={18} className="mr-3" />
              <span>Quản lý danh mục</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('backup')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'backup' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <Archive size={18} className="mr-3" />
              <span>Sao lưu & phục hồi</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('reports')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'reports' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <BarChart2 size={18} className="mr-3" />
              <span>Báo cáo điểm danh</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('support')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'support' ? 'bg-indigo-700' : 'hover:bg-indigo-700'}`}
            >
              <HelpCircle size={18} className="mr-3" />
              <span>Hỗ trợ kỹ thuật</span>
            </button>
          </nav>

          <div className="absolute bottom-0 left-0 right-0 p-4">
            <button className="flex items-center px-4 py-3 w-full rounded hover:bg-indigo-700 text-white">
              <LogOut size={18} className="mr-3" />
              <span>Đăng xuất</span>
            </button>
          </div>
        </div>
      </div>

      {/* Main content */}
      <div className="flex-1 ml-0 md:ml-64 transition-all duration-300">
        {/* Header */}
        <header className="bg-white shadow-sm p-4 flex justify-between items-center">
          <h1 className="text-xl font-semibold text-gray-800">{(activeTab)}</h1>
          
          <div className="flex items-center space-x-4">
            <div className="relative">
              <button className="relative p-2 rounded-full hover:bg-gray-100">
                <Bell size={20} />
                {notificationCount > 0 && (
                  <span className="absolute top-1 right-1 bg-red-500 text-white text-xs rounded-full h-4 w-4 flex items-center justify-center">
                    {notificationCount}
                  </span>
                )}
              </button>
            </div>
            
            <div className="flex items-center space-x-2">
              <img src="/api/placeholder/32/32" alt="Profile" className="w-8 h-8 rounded-full" />
              <span className="text-sm font-medium text-gray-700 hidden sm:inline-block">Admin</span>
            </div>
          </div>
        </header>

        {/* Page content */}
        <main className="p-6">
          {renderContent()}
        </main>
      </div>
    </div>
  );
}
// Dashboard Content Component
function DashboardContent() {
  const stats = [
    { title: "Tổng số sinh viên", value: "5,837", change: "+2.5%", isPositive: true },
    { title: "Tỷ lệ điểm danh", value: "87.3%", change: "+1.2%", isPositive: true },
    { title: "Vắng không phép", value: "312", change: "-4.1%", isPositive: true },
    { title: "Báo cáo lỗi", value: "24", change: "+5", isPositive: false },
  ];

  return (
    <div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {stats.map((stat, index) => (
          <div key={index} className="bg-white rounded-lg shadow p-6">
            <h3 className="text-gray-500 text-sm font-medium">{stat.title}</h3>
            <div className="mt-2 flex items-baseline">
              <p className="text-3xl font-semibold text-gray-900">{stat.value}</p>
              <p className={`ml-2 text-sm ${stat.isPositive ? 'text-green-600' : 'text-red-600'}`}>
                {stat.change}
              </p>
            </div>
          </div>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-medium text-gray-900 mb-4">Tình trạng điểm danh theo khoa</h3>
          <div className="space-y-4">
            <div>
              <div className="flex justify-between mb-1">
                <span className="text-sm font-medium">Công nghệ thông tin</span>
                <span className="text-sm font-medium text-gray-500">92%</span>
              </div>
              <div className="w-full bg-gray-200 rounded-full h-2">
                <div className="bg-green-500 h-2 rounded-full" style={{ width: '92%' }}></div>
              </div>
            </div>
            <div>
              <div className="flex justify-between mb-1">
                <span className="text-sm font-medium">Quản trị kinh doanh</span>
                <span className="text-sm font-medium text-gray-500">85%</span>
              </div>
              <div className="w-full bg-gray-200 rounded-full h-2">
                <div className="bg-green-500 h-2 rounded-full" style={{ width: '85%' }}></div>
              </div>
            </div>
            <div>
              <div className="flex justify-between mb-1">
                <span className="text-sm font-medium">Kỹ thuật điện</span>
                <span className="text-sm font-medium text-gray-500">78%</span>
              </div>
              <div className="w-full bg-gray-200 rounded-full h-2">
                <div className="bg-yellow-500 h-2 rounded-full" style={{ width: '78%' }}></div>
              </div>
            </div>
            <div>
              <div className="flex justify-between mb-1">
                <span className="text-sm font-medium">Ngôn ngữ Anh</span>
                <span className="text-sm font-medium text-gray-500">81%</span>
              </div>
              <div className="w-full bg-gray-200 rounded-full h-2">
                <div className="bg-green-500 h-2 rounded-full" style={{ width: '81%' }}></div>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-medium text-gray-900 mb-4">Hoạt động gần đây</h3>
          <div className="space-y-4">
            <div className="flex">
              <div className="flex-shrink-0 w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center">
                <User size={16} className="text-blue-600" />
              </div>
              <div className="ml-3">
                <p className="text-sm text-gray-700">
                  <span className="font-medium">Nguyễn Văn A</span> đã tạo mới tài khoản giảng viên
                </p>
                <p className="text-xs text-gray-500">5 phút trước</p>
              </div>
            </div>
            <div className="flex">
              <div className="flex-shrink-0 w-8 h-8 rounded-full bg-green-100 flex items-center justify-center">
                <Settings size={16} className="text-green-600" />
              </div>
              <div className="ml-3">
                <p className="text-sm text-gray-700">
                  <span className="font-medium">Hệ thống</span> đã hoàn thành sao lưu dữ liệu
                </p>
                <p className="text-xs text-gray-500">30 phút trước</p>
              </div>
            </div>
            <div className="flex">
              <div className="flex-shrink-0 w-8 h-8 rounded-full bg-yellow-100 flex items-center justify-center">
                <AlertTriangle size={16} className="text-yellow-600" />
              </div>
              <div className="ml-3">
                <p className="text-sm text-gray-700">
                  <span className="font-medium">Cảnh báo</span> phát hiện nhiều lần đăng nhập thất bại từ IP 192.168.1.45
                </p>
                <p className="text-xs text-gray-500">2 giờ trước</p>
              </div>
            </div>
            <div className="flex">
              <div className="flex-shrink-0 w-8 h-8 rounded-full bg-purple-100 flex items-center justify-center">
                <BookOpen size={16} className="text-purple-600" />
              </div>
              <div className="ml-3">
                <p className="text-sm text-gray-700">
                  <span className="font-medium">Trần Thị B</span> đã cập nhật thông tin môn học "Lập trình web nâng cao"
                </p>
                <p className="text-xs text-gray-500">4 giờ trước</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

// User Management Component
function UserManagement() {
  const [activeTab, setActiveTab] = useState('all');
  const [searchTerm, setSearchTerm] = useState('');
  
  const users = [
    { id: 1, name: "Nguyễn Văn A", email: "nguyenvana@stu.edu.vn", role: "Giảng viên", status: "Hoạt động", lastLogin: "Hôm nay, 09:45" },
    { id: 2, name: "Trần Thị B", email: "tranthib@stu.edu.vn", role: "Quản trị viên", status: "Hoạt động", lastLogin: "Hôm nay, 08:30" },
    { id: 3, name: "Lê Văn C", email: "levanc@stu.edu.vn", role: "Giảng viên", status: "Khóa", lastLogin: "12/04/2025" },
    { id: 4, name: "Phạm Thị D", email: "phamthid@stu.edu.vn", role: "Sinh viên", status: "Hoạt động", lastLogin: "Hôm qua, 15:20" },
    { id: 5, name: "Hoàng Văn E", email: "hoangvane@stu.edu.vn", role: "Giảng viên", status: "Hoạt động", lastLogin: "Hôm qua, 10:12" },
  ];

  const filteredUsers = users.filter(user => {
    const matchesSearch = user.name.toLowerCase().includes(searchTerm.toLowerCase()) || 
                          user.email.toLowerCase().includes(searchTerm.toLowerCase());
    
    if (activeTab === 'all') return matchesSearch;
    if (activeTab === 'active') return matchesSearch && user.status === 'Hoạt động';
    if (activeTab === 'locked') return matchesSearch && user.status === 'Khóa';
    return matchesSearch;
  });

  return (
    <div>
      <div className="mb-6 flex justify-between items-center">
        <h2 className="text-2xl font-bold text-gray-800">Quản lý tài khoản người dùng</h2>
        <button className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
          Thêm người dùng mới
        </button>
      </div>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <div className="p-4 border-b border-gray-200">
          <div className="flex flex-col md:flex-row md:items-center md:justify-between">
            <div className="flex space-x-4 mb-4 md:mb-0">
              <button 
                className={`px-3 py-2 text-sm font-medium rounded-md ${activeTab === 'all' ? 'bg-indigo-50 text-indigo-700' : 'text-gray-500 hover:text-gray-700'}`}
                onClick={() => setActiveTab('all')}
              >
                Tất cả
              </button>
              <button 
                className={`px-3 py-2 text-sm font-medium rounded-md ${activeTab === 'active' ? 'bg-indigo-50 text-indigo-700' : 'text-gray-500 hover:text-gray-700'}`}
                onClick={() => setActiveTab('active')}
              >
                Đang hoạt động
              </button>
              <button 
                className={`px-3 py-2 text-sm font-medium rounded-md ${activeTab === 'locked' ? 'bg-indigo-50 text-indigo-700' : 'text-gray-500 hover:text-gray-700'}`}
                onClick={() => setActiveTab('locked')}
              >
                Đã khóa
              </button>
            </div>
            <div className="relative">
              <input
                type="text"
                placeholder="Tìm kiếm người dùng..."
                className="w-full md:w-64 pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
              <div className="absolute left-3 top-2.5 text-gray-400">
                <Search size={18} />
              </div>
            </div>
          </div>
        </div>
        
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Tên người dùng
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Email
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Vai trò
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Trạng thái
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Đăng nhập gần nhất
                </th>
                <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Thao tác
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {filteredUsers.map((user) => (
                <tr key={user.id}>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div className="flex-shrink-0 h-10 w-10 bg-indigo-100 rounded-full flex items-center justify-center">
                        <span className="text-indigo-700 font-medium">{user.name.charAt(0)}</span>
                      </div>
                      <div className="ml-4">
                        <div className="text-sm font-medium text-gray-900">{user.name}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm text-gray-500">{user.email}</div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm text-gray-900">{user.role}</div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                      user.status === 'Hoạt động' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                    }`}>
                      {user.status}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {user.lastLogin}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                    <button className="text-indigo-600 hover:text-indigo-900 mr-3">Chỉnh sửa</button>
                    {user.status === 'Hoạt động' ? (
                      <button className="text-red-600 hover:text-red-900 mr-3">Khóa</button>
                    ) : (
                      <button className="text-green-600 hover:text-green-900 mr-3">Mở khóa</button>
                    )}
                    <button className="text-indigo-600 hover:text-indigo-900">Đặt lại mật khẩu</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
          <div className="flex-1 flex justify-between sm:hidden">
            <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
              Trước
            </button>
            <button className="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
              Tiếp
            </button>
          </div>
          <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
            <div>
              <p className="text-sm text-gray-700">
                Hiển thị <span className="font-medium">1</span> đến <span className="font-medium">5</span> của <span className="font-medium">12</span> kết quả
              </p>
            </div>
            <div>
              <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                <button className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                  <span className="sr-only">Previous</span>
                  {/* Chevron left icon */}
                  <svg className="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fillRule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clipRule="evenodd" />
                  </svg>
                </button>
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-indigo-50 text-sm font-medium text-indigo-600 hover:bg-gray-50">
                  1
                </button>
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                  2
                </button>
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                  3
                </button>
                <button className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                  <span className="sr-only">Next</span>
                  {/* Chevron right icon */}
                  <svg className="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
                  </svg>
                </button>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

// Permission Management Component
function PermissionManagement() {
  const [selectedRole, setSelectedRole] = useState('admin');
  
  const permissions = [
    { id: 'user_view', name: 'Xem thông tin người dùng', admin: true, teacher: true, student: false },
    { id: 'user_create', name: 'Tạo người dùng mới', admin: true, teacher: false, student: false },
    { id: 'user_edit', name: 'Chỉnh sửa thông tin người dùng', admin: true, teacher: false, student: false },
    { id: 'user_delete', name: 'Xóa người dùng', admin: true, teacher: false, student: false },
    { id: 'attendance_view', name: 'Xem thông tin điểm danh', admin: true, teacher: true, student: true },
    { id: 'attendance_create', name: 'Tạo phiên điểm danh', admin: true, teacher: true, student: false },
    { id: 'attendance_edit', name: 'Chỉnh sửa thông tin điểm danh', admin: true, teacher: true, student: false },
    { id: 'report_view', name: 'Xem báo cáo', admin: true, teacher: true, student: false },
    // { id: 'report_create', name: 'Tạo báo cáo mới', admin: true, teacher: false, student: false },
    { id: 'system_settings', name: 'Thay đổi cài đặt hệ thống', admin: true, teacher: false, student: false },
    { id: 'backup_restore', name: 'Sao lưu và phục hồi dữ liệu', admin: true, teacher: false, student: false },
  ];
  
  return (
    <div>
      <div className="mb-6">
        <h2 className="text-2xl font-bold text-gray-800">Quản lý phân quyền</h2>
      </div>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <div className="p-4 border-b border-gray-200">
          <div className="flex space-x-4">
            <button 
              className={`px-4 py-2 text-sm font-medium rounded-md ${selectedRole === 'admin' ? 'bg-indigo-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
              onClick={() => setSelectedRole('admin')}
            >
              Quản trị viên
            </button>
            <button 
              className={`px-4 py-2 text-sm font-medium rounded-md ${selectedRole === 'teacher' ? 'bg-indigo-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
              onClick={() => setSelectedRole('teacher')}
            >
              Giảng viên
            </button>
            <button 
              className={`px-4 py-2 text-sm font-medium rounded-md ${selectedRole === 'student' ? 'bg-indigo-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
              onClick={() => setSelectedRole('student')}
            >
              Sinh viên
            </button>
            <button 
              className={`px-4 py-2 text-sm font-medium rounded-md ${selectedRole === 'custom' ? 'bg-indigo-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
              onClick={() => setSelectedRole('custom')}
            >
              Tùy chỉnh
            </button>
          </div>
        </div>
        
        <div className="p-6">
          <div className="mb-6">
            <p className="text-gray-700 mb-2">Đang chỉnh sửa quyền cho nhóm: <span className="font-semibold">{
              selectedRole === 'admin' ? 'Quản trị viên' : 
              selectedRole === 'teacher' ? 'Giảng viên' : 
              selectedRole === 'student' ? 'Sinh viên' : 'Tùy chỉnh'
            }</span></p>
            {selectedRole === 'custom' && (
              <div className="flex items-center mt-2">
                <input
                  type="text"
                  placeholder="Tên nhóm quyền mới"
                  className="flex-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                />
                <button className="ml-2 bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
                  Tạo nhóm mới
                </button>
              </div>
            )}
          </div>
          
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Quyền hạn
                  </th>
                  <th scope="col" className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider w-20">
                    Trạng thái
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {permissions.map((permission) => (
                  <tr key={permission.id}>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm font-medium text-gray-900">{permission.name}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-center">
                      <input 
                        type="checkbox" 
                        className="h-5 w-5 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        checked={
                          selectedRole === 'admin' ? permission.admin :
                          selectedRole === 'teacher' ? permission.teacher :
                          selectedRole === 'student' ? permission.student : false
                        }
                        onChange={() => {}}
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          <div className="mt-6 flex justify-end">
            <button className="bg-white text-gray-700 border border-gray-300 px-4 py-2 rounded-md hover:bg-gray-50 transition-colors mr-2">
              Hủy
            </button>
            <button className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
              Lưu thay đổi
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

// User Logs Component
function UserLogs() {
  const [dateFilter, setDateFilter] = useState('today');
  const [userFilter, setUserFilter] = useState('all');
  
  const logs = [
    { id: 1, user: "Trần Thị B", action: "Đăng nhập", details: "Đăng nhập thành công", time: "09:45:12", date: "04/05/2025", ip: "192.168.1.25" },
    { id: 2, user: "Nguyễn Văn A", action: "Chỉnh sửa", details: "Cập nhật thông tin người dùng #142", time: "09:32:45", date: "04/05/2025", ip: "192.168.1.30" },
    { id: 3, user: "Hệ thống", action: "Sao lưu", details: "Sao lưu dữ liệu tự động", time: "09:00:00", date: "04/05/2025", ip: "127.0.0.1" },
    { id: 4, user: "Lê Văn C", action: "Đăng nhập", details: "Đăng nhập thất bại (sai mật khẩu)", time: "08:52:17", date: "04/05/2025", ip: "192.168.1.45" },
    { id: 5, user: "Phạm Thị D", action: "Điểm danh", details: "Điểm danh môn Lập trình web", time: "08:30:22", date: "04/05/2025", ip: "192.168.1.55" },
    { id: 6, user: "Hoàng Văn E", action: "Đăng nhập", details: "Đăng nhập thành công", time: "17:45:12", date: "03/05/2025", ip: "192.168.1.60" },
    { id: 7, user: "Trần Thị B", action: "Đăng xuất", details: "Đăng xuất thành công", time: "17:30:45", date: "03/05/2025", ip: "192.168.1.25" },
  ];

  return (
    <div>
      <div className="mb-6 flex justify-between items-center">
        <h2 className="text-2xl font-bold text-gray-800">Nhật ký hoạt động</h2>
        <button className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
          Xuất báo cáo
        </button>
      </div>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <div className="p-4 border-b border-gray-200">
          <div className="flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
            <div className="flex flex-col md:flex-row md:items-center">
              <label className="text-sm font-medium text-gray-700 mr-2">Thời gian:</label>
              <select 
                className="mt-1 md:mt-0 block w-full md:w-auto pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                value={dateFilter}
                onChange={(e) => setDateFilter(e.target.value)}
              >
                <option value="today">Hôm nay</option>
                <option value="yesterday">Hôm qua</option>
                <option value="week">7 ngày qua</option>
                <option value="month">30 ngày qua</option>
                <option value="custom">Tùy chỉnh...</option>
              </select>
            </div>
            <div className="flex flex-col md:flex-row md:items-center">
              <label className="text-sm font-medium text-gray-700 mr-2">Người dùng:</label>
              <select 
                className="mt-1 md:mt-0 block w-full md:w-auto pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                value={userFilter}
                onChange={(e) => setUserFilter(e.target.value)}
              >
                <option value="all">Tất cả</option>
                <option value="system">Hệ thống</option>
                <option value="admin">Quản trị viên</option>
                <option value="teacher">Giảng viên</option>
                <option value="student">Sinh viên</option>
              </select>
            </div>
            <div className="flex flex-col md:flex-row md:items-center">
              <label className="text-sm font-medium text-gray-700 mr-2">Hành động:</label>
              <select 
                className="mt-1 md:mt-0 block w-full md:w-auto pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
              >
                <option value="all">Tất cả</option>
                <option value="login">Đăng nhập</option>
                <option value="logout">Đăng xuất</option>
                <option value="create">Tạo mới</option>
                <option value="edit">Chỉnh sửa</option>
                <option value="delete">Xóa</option>
                <option value="backup">Sao lưu</option>
              </select>
            </div>
            <div className="flex-1 md:text-right">
              <button className="bg-indigo-50 text-indigo-700 px-4 py-2 rounded-md hover:bg-indigo-100 transition-colors">
                Lọc
              </button>
            </div>
          </div>
        </div>
        
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Người dùng
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Hành động
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Chi tiết
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Thời gian
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Địa chỉ IP
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {logs.map((log) => (
                <tr key={log.id}>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm font-medium text-gray-900">{log.user}</div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full 
                      ${log.action === 'Đăng nhập' && log.details.includes('thất bại') ? 'bg-red-100 text-red-800' : 
                        log.action === 'Sao lưu' ? 'bg-blue-100 text-blue-800' :
                        log.action === 'Chỉnh sửa' ? 'bg-yellow-100 text-yellow-800' :
                        'bg-green-100 text-green-800'}`}>
                      {log.action}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm text-gray-900">{log.details}</div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm text-gray-500">{log.date}, {log.time}</div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {log.ip}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-200">
          <div className="flex-1 flex justify-between">
            <div>
              <p className="text-sm text-gray-700">
                Hiển thị <span className="font-medium">1</span> đến <span className="font-medium">7</span> của <span className="font-medium">120</span> kết quả
              </p>
            </div>
            <div>
              <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                <button className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                  <span className="sr-only">Previous</span>
                  <svg className="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fillRule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clipRule="evenodd" />
                  </svg>
                </button>
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-indigo-50 text-sm font-medium text-indigo-600 hover:bg-gray-50">
                  1
                </button>
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                  2
                </button>
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                  3
                </button>
                <button className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                  <span className="sr-only">Next</span>
                  <svg className="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
                  </svg>
                </button>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  );

}
// CategoryManagement Component
function CategoryManagement() {

    
    const categoryTypes = [
        { id: 'faculty', name: 'Khoa' },
        { id: 'major', name: 'Ngành' },
        { id: 'class', name: 'Lớp' },
        { id: 'subject', name: 'Môn học' }
      ];
    
      // State cho loại danh mục đang được chọn
      const [selectedType, setSelectedType] = useState('faculty');
      const [searchTerm, setSearchTerm] = useState('');
      const [statusFilter, setStatusFilter] = useState('all');
      const [showAddModal, setShowAddModal] = useState(false);
      const [showEditModal, setShowEditModal] = useState(false);
      const [currentItem, setCurrentItem] = useState(null);
    
      // Dữ liệu mẫu cho các loại danh mục
      const data = {
        faculty: [
          { id: 1, name: "Khoa Công nghệ thông tin", count: 25, status: "active" },
          { id: 2, name: "Khoa Kỹ thuật điện", count: 18, status: "active" },
          { id: 3, name: "Khoa Kinh tế", count: 32, status: "active" },
          { id: 4, name: "Khoa Ngoại ngữ", count: 15, status: "inactive" },
          { id: 5, name: "Khoa Quản trị kinh doanh", count: 21, status: "active" },
          { id: 6, name: "Khoa Khoa học cơ bản", count: 12, status: "active" },
          { id: 7, name: "Khoa Xây dựng", count: 8, status: "inactive" },
        ],
        major: [
          { id: 1, name: "Công nghệ phần mềm", facultyId: 1, count: 120, status: "active" },
          { id: 2, name: "Hệ thống thông tin", facultyId: 1, count: 85, status: "active" },
          { id: 3, name: "Kỹ thuật điện tử", facultyId: 2, count: 65, status: "active" },
          { id: 4, name: "Tự động hóa", facultyId: 2, count: 45, status: "inactive" },
          { id: 5, name: "Kinh tế đối ngoại", facultyId: 3, count: 95, status: "active" },
        ],
        class: [
          { id: 1, name: "CNTT.K18.A", majorId: 1, count: 30, status: "active" },
          { id: 2, name: "CNTT.K18.B", majorId: 1, count: 32, status: "active" },
          { id: 3, name: "HTTT.K19.A", majorId: 2, count: 28, status: "active" },
          { id: 4, name: "KTĐT.K17.A", majorId: 3, count: 25, status: "inactive" },
          { id: 5, name: "KTĐN.K18.A", majorId: 5, count: 35, status: "active" },
        ],
        subject: [
          { id: 1, name: "Lập trình cơ bản", majorId: 1, credits: 3, count: 150, status: "active" },
          { id: 2, name: "Cơ sở dữ liệu", majorId: 1, credits: 4, count: 120, status: "active" },
          { id: 3, name: "Phân tích thiết kế hệ thống", majorId: 2, credits: 4, count: 85, status: "active" },
          { id: 4, name: "Mạch điện tử", majorId: 3, credits: 3, count: 60, status: "inactive" },
          { id: 5, name: "Kinh tế vĩ mô", majorId: 5, credits: 3, count: 100, status: "active" },
        ]
      };
    
      // Lọc dữ liệu theo tìm kiếm và trạng thái
      const filteredData = data[selectedType].filter(item => {
        return (
          item.name.toLowerCase().includes(searchTerm.toLowerCase()) &&
          (statusFilter === 'all' || item.status === statusFilter)
        );
      });
    
      // Xử lý khi click nút sửa
      const handleEdit = (item) => {
        setCurrentItem(item);
        setShowEditModal(true);
      };
    
      // Xử lý khi click nút thêm mới
      const handleAdd = () => {
        setShowAddModal(true);
      };
    
      // Hiển thị tiêu đề tương ứng với loại danh mục đang chọn
      const getTitle = () => {
        const type = categoryTypes.find(t => t.id === selectedType);
        return `Quản lý ${type.name}`;
      };
    
      return (
        <div className="p-6 max-w-7xl mx-auto">
          {/* Tab chọn loại danh mục */}
          <div className="mb-6 border-b border-gray-200">
            <nav className="-mb-px flex space-x-8" aria-label="Tabs">
              {categoryTypes.map((type) => (
                <button
                  key={type.id}
                  onClick={() => setSelectedType(type.id)}
                  className={`${
                    selectedType === type.id
                      ? 'border-indigo-500 text-indigo-600'
                      : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  } whitespace-nowrap py-4 px-1 border-b-2 font-medium text-sm`}
                >
                  {type.name}
                </button>
              ))}
            </nav>
          </div>
    
          {/* Tiêu đề và nút thêm mới */}
          <div className="mb-6 flex justify-between items-center">
            <h2 className="text-2xl font-bold text-gray-800">{getTitle()}</h2>
            <button 
              onClick={handleAdd}
              className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors"
            >
              Thêm {categoryTypes.find(t => t.id === selectedType).name} mới
            </button>
          </div>
    
          {/* Bộ lọc và tìm kiếm */}
          <div className="bg-white rounded-lg shadow overflow-hidden">
            <div className="p-4 border-b border-gray-200">
              <div className="flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
                <div className="flex-1">
                  <input
                    type="text"
                    placeholder={`Tìm kiếm ${categoryTypes.find(t => t.id === selectedType).name.toLowerCase()}...`}
                    className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                  />
                </div>
                <div>
                  <select 
                    className="block w-full md:w-auto pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                    value={statusFilter}
                    onChange={(e) => setStatusFilter(e.target.value)}
                  >
                    <option value="all">Tất cả trạng thái</option>
                    <option value="active">Hoạt động</option>
                    <option value="inactive">Không hoạt động</option>
                  </select>
                </div>
              </div>
            </div>
            
            {/* Bảng dữ liệu */}
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      ID
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Tên {categoryTypes.find(t => t.id === selectedType).name}
                    </th>
                    {selectedType === 'subject' && (
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Số tín chỉ
                      </th>
                    )}
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      {selectedType === 'subject' ? 'Số sinh viên' : 'Số lượng'}
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Trạng thái
                    </th>
                    <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Thao tác
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {filteredData.map((item) => (
                    <tr key={item.id}>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="text-sm text-gray-900">{item.id}</div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="text-sm font-medium text-gray-900">{item.name}</div>
                      </td>
                      {selectedType === 'subject' && (
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-900">{item.credits}</div>
                        </td>
                      )}
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="text-sm text-gray-900">{item.count}</div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                          item.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                        }`}>
                          {item.status === 'active' ? 'Hoạt động' : 'Không hoạt động'}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <button 
                          className="text-indigo-600 hover:text-indigo-900 mr-3"
                          onClick={() => handleEdit(item)}
                        >
                          Sửa
                        </button>
                        <button className="text-red-600 hover:text-red-900">Xóa</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
    
            {/* Phân trang */}
            <div className="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-200">
              <div className="flex-1 flex justify-between sm:hidden">
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
                  Trước
                </button>
                <button className="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
                  Sau
                </button>
              </div>
              <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
                <div>
                  <p className="text-sm text-gray-700">
                    Hiển thị <span className="font-medium">1</span> đến <span className="font-medium">{filteredData.length}</span> của <span className="font-medium">{filteredData.length}</span> kết quả
                  </p>
                </div>
                <div>
                  <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                    <button className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                      <span className="sr-only">Previous</span>
                      <svg className="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                        <path fillRule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clipRule="evenodd" />
                      </svg>
                    </button>
                    <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-indigo-50 text-sm font-medium text-indigo-600 hover:bg-gray-50">
                      1
                    </button>
                    <button className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                      <span className="sr-only">Next</span>
                      <svg className="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                        <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
                      </svg>
                    </button>
                  </nav>
                </div>
              </div>
            </div>
          </div>
    
          {/* Modal thêm mới */}
          {showAddModal && (
            <div className="fixed z-10 inset-0 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true">
              <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
                <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
                <span className="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
                <div className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                  <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <div className="sm:flex sm:items-start">
                      <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left w-full">
                        <h3 className="text-lg leading-6 font-medium text-gray-900" id="modal-title">
                          Thêm {categoryTypes.find(t => t.id === selectedType).name} mới
                        </h3>
                        <div className="mt-4">
                          <div className="mb-4">
                            <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                              Tên {categoryTypes.find(t => t.id === selectedType).name}
                            </label>
                            <input
                              type="text"
                              id="name"
                              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            />
                          </div>
    
                          {selectedType !== 'faculty' && (
                            <div className="mb-4">
                              <label htmlFor="parent" className="block text-sm font-medium text-gray-700">
                                {selectedType === 'major' ? 'Khoa' : selectedType === 'class' ? 'Ngành' : 'Ngành'}
                              </label>
                              <select
                                id="parent"
                                className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                              >
                                {selectedType === 'major' && data.faculty.map(f => (
                                  <option key={f.id} value={f.id}>{f.name}</option>
                                ))}
                                {selectedType === 'class' && data.major.map(m => (
                                  <option key={m.id} value={m.id}>{m.name}</option>
                                ))}
                                {selectedType === 'subject' && data.major.map(m => (
                                  <option key={m.id} value={m.id}>{m.name}</option>
                                ))}
                              </select>
                            </div>
                          )}
    
                          {selectedType === 'subject' && (
                            <div className="mb-4">
                              <label htmlFor="credits" className="block text-sm font-medium text-gray-700">
                                Số tín chỉ
                              </label>
                              <input
                                type="number"
                                id="credits"
                                min="1"
                                max="10"
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                              />
                            </div>
                          )}
    
                          <div className="mb-4">
                            <label htmlFor="status" className="block text-sm font-medium text-gray-700">
                              Trạng thái
                            </label>
                            <select
                              id="status"
                              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                            >
                              <option value="active">Hoạt động</option>
                              <option value="inactive">Không hoạt động</option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button 
                      type="button" 
                      className="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-indigo-600 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:ml-3 sm:w-auto sm:text-sm"
                    >
                      Lưu
                    </button>
                    <button 
                      type="button" 
                      onClick={() => setShowAddModal(false)}
                      className="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
                    >
                      Hủy bỏ
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}
    
          {/* Modal chỉnh sửa */}
          {showEditModal && currentItem && (
            <div className="fixed z-10 inset-0 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true">
              <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
                <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
                <span className="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
                <div className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                  <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <div className="sm:flex sm:items-start">
                      <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left w-full">
                        <h3 className="text-lg leading-6 font-medium text-gray-900" id="modal-title">
                          Chỉnh sửa {categoryTypes.find(t => t.id === selectedType).name}
                        </h3>
                        <div className="mt-4">
                          <div className="mb-4">
                            <label htmlFor="editName" className="block text-sm font-medium text-gray-700">
                              Tên {categoryTypes.find(t => t.id === selectedType).name}
                            </label>
                            <input
                              type="text"
                              id="editName"
                              defaultValue={currentItem.name}
                              className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            />
                          </div>
    
                          {selectedType !== 'faculty' && (
                            <div className="mb-4">
                              <label htmlFor="editParent" className="block text-sm font-medium text-gray-700">
                                {selectedType === 'major' ? 'Khoa' : selectedType === 'class' ? 'Ngành' : 'Ngành'}
                              </label>
                              <select
                                id="editParent"
                                defaultValue={
                                  selectedType === 'major' ? currentItem.facultyId : 
                                  selectedType === 'class' ? currentItem.majorId : 
                                  currentItem.majorId
                                }
                                className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                              >
                                {selectedType === 'major' && data.faculty.map(f => (
                                  <option key={f.id} value={f.id}>{f.name}</option>
                                ))}
                                {selectedType === 'class' && data.major.map(m => (
                                  <option key={m.id} value={m.id}>{m.name}</option>
                                ))}
                                {selectedType === 'subject' && data.major.map(m => (
                                  <option key={m.id} value={m.id}>{m.name}</option>
                                ))}
                              </select>
                            </div>
                          )}
    
                          {selectedType === 'subject' && (
                            <div className="mb-4">
                              <label htmlFor="editCredits" className="block text-sm font-medium text-gray-700">
                                Số tín chỉ
                              </label>
                              <input
                                type="number"
                                id="editCredits"
                                defaultValue={currentItem.credits}
                                min="1"
                                max="10"
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm py-2 px-3 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                              />
                            </div>
                          )}
    
                          <div className="mb-4">
                            <label htmlFor="editStatus" className="block text-sm font-medium text-gray-700">
                              Trạng thái
                            </label>
                            <select
                              id="editStatus"
                              defaultValue={currentItem.status}
                              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                            >
                              <option value="active">Hoạt động</option>
                              <option value="inactive">Không hoạt động</option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button 
                      type="button" 
                      className="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-indigo-600 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:ml-3 sm:w-auto sm:text-sm"
                    >
                      Cập nhật
                    </button>
                    <button 
                      type="button" 
                      onClick={() => setShowEditModal(false)}
                      className="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
                    >
                      Hủy bỏ
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      );
    }

  
  // BackupRestore Component
  function BackupRestore() {
    const [backupOption, setBackupOption] = useState('auto');
    
    const backupHistory = [
      { id: 1, name: "Sao lưu tự động", date: "04/05/2025", time: "03:00:00", size: "256 MB", status: "success" },
      { id: 2, name: "Sao lưu tự động", date: "03/05/2025", time: "03:00:00", size: "255 MB", status: "success" },
      { id: 3, name: "Sao lưu thủ công", date: "02/05/2025", time: "14:30:25", size: "252 MB", status: "success" },
      { id: 4, name: "Sao lưu tự động", date: "02/05/2025", time: "03:00:00", size: "254 MB", status: "failed" },
      { id: 5, name: "Sao lưu tự động", date: "01/05/2025", time: "03:00:00", size: "251 MB", status: "success" },
    ];
  
    return (
      <div>
        <div className="mb-6 flex justify-between items-center">
          <h2 className="text-2xl font-bold text-gray-800">Sao lưu & Phục hồi</h2>
          <div className="space-x-2">
            <button className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition-colors">
              Sao lưu ngay
            </button>
            <button className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
              Phục hồi
            </button>
          </div>
        </div>
  
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2 bg-white rounded-lg shadow">
            <div className="p-6">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Lịch sử sao lưu</h3>
              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                  <thead className="bg-gray-50">
                    <tr>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Tên
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Ngày giờ
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Kích thước
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Trạng thái
                      </th>
                      <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Thao tác
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    {backupHistory.map((backup) => (
                      <tr key={backup.id}>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm font-medium text-gray-900">{backup.name}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-500">{backup.date}, {backup.time}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-900">{backup.size}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                            backup.status === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                          }`}>
                            {backup.status === 'success' ? 'Thành công' : 'Thất bại'}
                          </span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                          <button className="text-indigo-600 hover:text-indigo-900 mr-3">Tải về</button>
                          <button className="text-green-600 hover:text-green-900 mr-3">Phục hồi</button>
                          <button className="text-red-600 hover:text-red-900">Xóa</button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          
          <div className="bg-white rounded-lg shadow">
            <div className="p-6">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Cài đặt sao lưu</h3>
              <div className="space-y-4">
                <div>
                  <p className="block text-sm font-medium text-gray-700 mb-2">Phương thức sao lưu</p>
                  <div className="space-y-2">
                    <div className="flex items-center">
                      <input
                        id="auto-backup"
                        name="backup-type"
                        type="radio"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                        checked={backupOption === 'auto'}
                        onChange={() => setBackupOption('auto')}
                      />
                      <label htmlFor="auto-backup" className="ml-3 block text-sm font-medium text-gray-700">
                        Sao lưu tự động
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="manual-backup"
                        name="backup-type"
                        type="radio"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                        checked={backupOption === 'manual'}
                        onChange={() => setBackupOption('manual')}
                      />
                      <label htmlFor="manual-backup" className="ml-3 block text-sm font-medium text-gray-700">
                        Sao lưu thủ công
                      </label>
                    </div>
                  </div>
                </div>
                
                {backupOption === 'auto' && (
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tần suất sao lưu</label>
                    <select className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500">
                      <option value="daily">Hàng ngày</option>
                      <option value="weekly">Hàng tuần</option>
                      <option value="biweekly">Hai tuần một lần</option>
                      <option value="monthly">Hàng tháng</option>
                    </select>
                  </div>
                )}
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Vị trí lưu</label>
                  <select className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500">
                    <option value="local">Máy chủ cục bộ</option>
                    <option value="cloud">Đám mây</option>
                    <option value="both">Cả hai</option>
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Dữ liệu sao lưu</label>
                  <div className="space-y-2 mt-2">
                    <div className="flex items-center">
                      <input
                        id="backup-database"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="backup-database" className="ml-2 block text-sm text-gray-700">
                        Cơ sở dữ liệu
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="backup-files"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="backup-files" className="ml-2 block text-sm text-gray-700">
                        Tệp tin đính kèm
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="backup-logs"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="backup-logs" className="ml-2 block text-sm text-gray-700">
                        Nhật ký hệ thống
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="backup-config"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="backup-config" className="ml-2 block text-sm text-gray-700">
                        Cấu hình hệ thống
                      </label>
                    </div>
                  </div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Số bản sao lưu giữ lại</label>
                  <input
                    type="number"
                    className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                    defaultValue="10"
                  />
                  <p className="mt-1 text-xs text-gray-500">Các bản sao lưu cũ nhất sẽ tự động bị xóa khi vượt quá số lượng này</p>
                </div>
                
                <div className="pt-4">
                  <button className="w-full bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
                    Lưu cài đặt
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
  function AttendanceReports() {
    const [reportType, setReportType] = useState('monthly');
    const [dateRange, setDateRange] = useState('current-month');
    
    const reportHistory = [
      { id: 1, name: "Báo cáo điểm danh tháng 05/2025", date: "01/05/2025", type: "Báo cáo tháng", format: "PDF", status: "completed" },
      { id: 2, name: "Báo cáo điểm danh Q1/2025", date: "01/04/2025", type: "Báo cáo quý", format: "XLSX", status: "completed" },
      { id: 3, name: "Báo cáo điểm danh tuần 17/2025", date: "28/04/2025", type: "Báo cáo tuần", format: "PDF", status: "completed" },
      { id: 4, name: "Báo cáo điểm danh lớp học", date: "25/04/2025", type: "Báo cáo tùy chỉnh", format: "XLSX", status: "processing" },
      { id: 5, name: "Báo cáo điểm danh tuần 16/2025", date: "21/04/2025", type: "Báo cáo tuần", format: "PDF", status: "completed" },
    ];
  
    const classes = [
      "Lớp 10A1",
      "Lớp 10A2",
      "Lớp 11A1", 
      "Lớp 11A2",
      "Lớp 12A1"
    ];
  
    return (
      <div>
        {/* <div className="mb-6 flex justify-between items-center">
          <h2 className="text-2xl font-bold text-gray-800">Báo cáo điểm danh</h2>
          <div className="space-x-2">
            <button className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition-colors">
              Tạo báo cáo mới
            </button>
            <button className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
              Xuất báo cáo
            </button>
          </div>
        </div> */}
  
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2 bg-white rounded-lg shadow">
            <div className="p-6">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Lịch sử báo cáo</h3>
              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                  <thead className="bg-gray-50">
                    <tr>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Tên báo cáo
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Ngày tạo
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Loại báo cáo
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Định dạng
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Trạng thái
                      </th>
                      <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Thao tác
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    {reportHistory.map((report) => (
                      <tr key={report.id}>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm font-medium text-gray-900">{report.name}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-500">{report.date}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-900">{report.type}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm text-gray-900">{report.format}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                            report.status === 'completed' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'
                          }`}>
                            {report.status === 'completed' ? 'Hoàn thành' : 'Đang xử lý'}
                          </span>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                          <button className="text-indigo-600 hover:text-indigo-900 mr-3">Xem</button>
                          <button className="text-green-600 hover:text-green-900 mr-3">Tải về</button>
                          <button className="text-red-600 hover:text-red-900">Xóa</button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          
          <div className="bg-white rounded-lg shadow">
            <div className="p-6">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Tạo báo cáo điểm danh mới</h3>
              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Loại báo cáo</label>
                  <select 
                    className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                    value={reportType}
                    onChange={(e) => setReportType(e.target.value)}
                  >
                    <option value="daily">Báo cáo ngày</option>
                    <option value="weekly">Báo cáo tuần</option>
                    <option value="monthly">Báo cáo tháng</option>
                    <option value="quarterly">Báo cáo học kỳ</option>
                    <option value="annual">Báo cáo năm học</option>
                    <option value="custom">Báo cáo tùy chỉnh</option>
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Khoảng thời gian</label>
                  <select 
                    className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                    value={dateRange}
                    onChange={(e) => setDateRange(e.target.value)}
                  >
                    <option value="current-day">Ngày hôm nay</option>
                    <option value="current-week">Tuần này</option>
                    <option value="current-month">Tháng này</option>
                    <option value="last-month">Tháng trước</option>
                    <option value="current-quarter">Học kỳ hiện tại</option>
                    <option value="custom-range">Tùy chỉnh...</option>
                  </select>
                </div>
                
                {dateRange === 'custom-range' && (
                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Từ ngày</label>
                      <input
                        type="date"
                        className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Đến ngày</label>
                      <input
                        type="date"
                        className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      />
                    </div>
                  </div>
                )}
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Lớp học</label>
                  <select className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500">
                    <option value="all">Tất cả các lớp</option>
                    {classes.map((cls, index) => (
                      <option key={index} value={cls.toLowerCase().replace(/\s+/g, '-')}>
                        {cls}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Định dạng xuất</label>
                  <div className="space-y-2 mt-2">
                    <div className="flex items-center">
                      <input
                        id="format-pdf"
                        type="radio"
                        name="report-format"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                        defaultChecked
                      />
                      <label htmlFor="format-pdf" className="ml-2 block text-sm text-gray-700">
                        PDF
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="format-xlsx"
                        type="radio"
                        name="report-format"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                      />
                      <label htmlFor="format-xlsx" className="ml-2 block text-sm text-gray-700">
                        Excel (XLSX)
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="format-csv"
                        type="radio"
                        name="report-format"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                      />
                      <label htmlFor="format-csv" className="ml-2 block text-sm text-gray-700">
                        CSV
                      </label>
                    </div>
                  </div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Nội dung báo cáo</label>
                  <div className="space-y-2 mt-2">
                    <div className="flex items-center">
                      <input
                        id="include-summary"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="include-summary" className="ml-2 block text-sm text-gray-700">
                        Tổng kết điểm danh
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="include-late"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="include-late" className="ml-2 block text-sm text-gray-700">
                        Chi tiết đi học muộn
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="include-absence"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="include-absence" className="ml-2 block text-sm text-gray-700">
                        Thống kê vắng mặt
                      </label>
                    </div>
                    <div className="flex items-center">
                      <input
                        id="include-leave"
                        type="checkbox"
                        className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                        defaultChecked
                      />
                      <label htmlFor="include-leave" className="ml-2 block text-sm text-gray-700">
                        Báo phép/Nghỉ có lý do
                      </label>
                    </div>
                  </div>
                </div>
                
                <div className="pt-4">
                  <button className="w-full bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
                    Tạo báo cáo
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
  function TechnicalSupport() {
    const [searchTerm, setSearchTerm] = useState('');
    const [ticketType, setTicketType] = useState('all');
    
    const supportTickets = [
      { 
        id: 1, 
        title: "Không thể xuất báo cáo điểm danh lớp 11A2", 
        date: "03/05/2025", 
        priority: "high", 
        status: "open", 
        assignedTo: "Nguyễn Văn A",
        category: "Báo cáo"
      },
      { 
        id: 2, 
        title: "Lỗi đồng bộ dữ liệu điểm danh từ thiết bị", 
        date: "01/05/2025", 
        priority: "medium", 
        status: "in-progress", 
        assignedTo: "Trần Thị B",
        category: "Đồng bộ dữ liệu"
      },
      { 
        id: 3, 
        title: "Không thể đăng nhập vào hệ thống điểm danh", 
        date: "28/04/2025", 
        priority: "high", 
        status: "in-progress", 
        assignedTo: "Phạm Văn C",
        category: "Đăng nhập"
      },
      { 
        id: 4, 
        title: "Hướng dẫn cài đặt phần mềm điểm danh trên máy tính cá nhân", 
        date: "26/04/2025", 
        priority: "low", 
        status: "open", 
        assignedTo: "Chưa phân công",
        category: "Hướng dẫn"
      },
      { 
        id: 5, 
        title: "Lỗi hiển thị báo cáo trên thiết bị di động", 
        date: "25/04/2025", 
        priority: "medium", 
        status: "resolved", 
        assignedTo: "Nguyễn Văn A",
        category: "Giao diện"
      }
    ];
  
    const faqs = [
      {
        question: "Làm thế nào để tạo báo cáo điểm danh mới?",
        answer: "Để tạo báo cáo điểm danh mới, hãy vào trang Báo cáo điểm danh, nhấp vào nút 'Tạo báo cáo mới', chọn loại báo cáo, khoảng thời gian và lớp học bạn muốn tạo báo cáo, sau đó nhấn nút 'Tạo báo cáo'."
      },
      {
        question: "Làm cách nào để sửa lỗi đồng bộ dữ liệu từ thiết bị?",
        answer: "Để sửa lỗi đồng bộ dữ liệu, trước tiên hãy kiểm tra kết nối internet, sau đó đăng xuất và đăng nhập lại vào hệ thống. Nếu vẫn không khắc phục được, hãy khởi động lại thiết bị và thử lại. Nếu vấn đề vẫn tiếp diễn, hãy tạo một yêu cầu hỗ trợ kỹ thuật."
      },
      {
        question: "Làm thế nào để xuất báo cáo điểm danh ra file Excel?",
        answer: "Để xuất báo cáo ra file Excel, hãy vào trang Báo cáo điểm danh, tạo hoặc chọn báo cáo bạn muốn xuất, sau đó chọn định dạng 'Excel (XLSX)' trong phần cài đặt báo cáo và nhấn nút 'Xuất báo cáo'."
      },
      {
        question: "Làm cách nào để thêm học sinh mới vào hệ thống điểm danh?",
        answer: "Để thêm học sinh mới, hãy vào phần 'Quản lý học sinh', nhấp vào nút 'Thêm học sinh mới', điền đầy đủ thông tin cần thiết như họ tên, lớp, mã học sinh và các thông tin khác, sau đó nhấn 'Lưu'."
      },
      {
        question: "Tần suất đồng bộ dữ liệu điểm danh là bao lâu?",
        answer: "Hệ thống sẽ tự động đồng bộ dữ liệu điểm danh mỗi 15 phút. Tuy nhiên, bạn cũng có thể chọn đồng bộ thủ công bằng cách nhấn vào nút 'Đồng bộ ngay' trong giao diện điểm danh."
      }
    ];
  
    const filteredTickets = supportTickets.filter(ticket => {
      const matchesSearch = ticket.title.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesType = ticketType === 'all' || ticket.status === ticketType;
      return matchesSearch && matchesType;
    });
  
    return (
      <div>
        <div className="mb-6 flex justify-between items-center">
          <h2 className="text-2xl font-bold text-gray-800">Hỗ trợ kỹ thuật</h2>
          <button className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
            Tạo yêu cầu hỗ trợ mới
          </button>
        </div>
  
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2 space-y-6">
            <div className="bg-white rounded-lg shadow">
              <div className="p-6">
                <h3 className="text-lg font-medium text-gray-900 mb-4">Yêu cầu hỗ trợ của tôi</h3>
                
                <div className="mb-4 flex flex-col sm:flex-row gap-3">
                  <div className="flex-grow">
                    <input
                      type="text"
                      placeholder="Tìm kiếm yêu cầu hỗ trợ..."
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      value={searchTerm}
                      onChange={(e) => setSearchTerm(e.target.value)}
                    />
                  </div>
                  <div>
                    <select
                      className="w-full sm:w-auto p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      value={ticketType}
                      onChange={(e) => setTicketType(e.target.value)}
                    >
                      <option value="all">Tất cả yêu cầu</option>
                      <option value="open">Đang mở</option>
                      <option value="in-progress">Đang xử lý</option>
                      <option value="resolved">Đã giải quyết</option>
                    </select>
                  </div>
                </div>
                
                <div className="overflow-x-auto">
                  <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                      <tr>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                          Tiêu đề
                        </th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                          Ngày tạo
                        </th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                          Độ ưu tiên
                        </th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                          Trạng thái
                        </th>
                        <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                          Thao tác
                        </th>
                      </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                      {filteredTickets.map((ticket) => (
                        <tr key={ticket.id}>
                          <td className="px-6 py-4 whitespace-nowrap">
                            <div className="text-sm font-medium text-gray-900">{ticket.title}</div>
                            <div className="text-sm text-gray-500">{ticket.category}</div>
                          </td>
                          <td className="px-6 py-4 whitespace-nowrap">
                            <div className="text-sm text-gray-500">{ticket.date}</div>
                          </td>
                          <td className="px-6 py-4 whitespace-nowrap">
                            <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                              ticket.priority === 'high' ? 'bg-red-100 text-red-800' : 
                              ticket.priority === 'medium' ? 'bg-yellow-100 text-yellow-800' : 
                              'bg-green-100 text-green-800'
                            }`}>
                              {ticket.priority === 'high' ? 'Cao' : 
                               ticket.priority === 'medium' ? 'Trung bình' : 'Thấp'}
                            </span>
                          </td>
                          <td className="px-6 py-4 whitespace-nowrap">
                            <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                              ticket.status === 'open' ? 'bg-blue-100 text-blue-800' : 
                              ticket.status === 'in-progress' ? 'bg-purple-100 text-purple-800' : 
                              'bg-green-100 text-green-800'
                            }`}>
                              {ticket.status === 'open' ? 'Đang mở' : 
                               ticket.status === 'in-progress' ? 'Đang xử lý' : 'Đã giải quyết'}
                            </span>
                          </td>
                          <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                            <button className="text-indigo-600 hover:text-indigo-900 mr-3">Xem</button>
                            <button className="text-green-600 hover:text-green-900 mr-3">Cập nhật</button>
                            <button className="text-red-600 hover:text-red-900">Đóng</button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            
            <div className="bg-white rounded-lg shadow">
              <div className="p-6">
                <h3 className="text-lg font-medium text-gray-900 mb-4">Các câu hỏi thường gặp</h3>
                <div className="space-y-4">
                  {faqs.map((faq, index) => (
                    <div key={index} className="border border-gray-200 rounded-lg p-4">
                      <h4 className="text-md font-semibold text-gray-800 mb-2">{faq.question}</h4>
                      <p className="text-sm text-gray-600">{faq.answer}</p>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
          
          <div className="space-y-6">
            <div className="bg-white rounded-lg shadow">
              <div className="p-6">
                <h3 className="text-lg font-medium text-gray-900 mb-4">Tạo yêu cầu hỗ trợ mới</h3>
                <div className="space-y-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tiêu đề</label>
                    <input
                      type="text"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      placeholder="Nhập tiêu đề yêu cầu hỗ trợ"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Danh mục</label>
                    <select className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500">
                      <option value="">Chọn danh mục</option>
                      <option value="login">Đăng nhập</option>
                      <option value="reports">Báo cáo</option>
                      <option value="sync">Đồng bộ dữ liệu</option>
                      <option value="ui">Giao diện</option>
                      <option value="guide">Hướng dẫn sử dụng</option>
                      <option value="other">Khác</option>
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Độ ưu tiên</label>
                    <select className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500">
                      <option value="low">Thấp</option>
                      <option value="medium">Trung bình</option>
                      <option value="high">Cao</option>
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Mô tả vấn đề</label>
                    <textarea
                      rows="5"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      placeholder="Mô tả chi tiết vấn đề bạn đang gặp phải..."
                    ></textarea>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Đính kèm ảnh chụp màn hình (nếu có)</label>
                    <div className="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
                      <div className="space-y-1 text-center">
                        <svg
                          className="mx-auto h-12 w-12 text-gray-400"
                          stroke="currentColor"
                          fill="none"
                          viewBox="0 0 48 48"
                          aria-hidden="true"
                        >
                          <path
                            d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02"
                            strokeWidth="2"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                          />
                        </svg>
                        <div className="flex text-sm text-gray-600">
                          <label
                            htmlFor="file-upload"
                            className="relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-indigo-500"
                          >
                            <span>Tải lên tệp</span>
                            <input id="file-upload" name="file-upload" type="file" className="sr-only" />
                          </label>
                          <p className="pl-1">hoặc kéo và thả</p>
                        </div>
                        <p className="text-xs text-gray-500">PNG, JPG, GIF tối đa 10MB</p>
                      </div>
                    </div>
                  </div>
                  
                  <div className="pt-4">
                    <button className="w-full bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
                      Gửi yêu cầu hỗ trợ
                    </button>
                  </div>
                </div>
              </div>
            </div>
            
            <div className="bg-white rounded-lg shadow">
              <div className="p-6">
                <h3 className="text-lg font-medium text-gray-900 mb-4">Thông tin liên hệ</h3>
                <div className="space-y-3">
                  <div className="flex items-center">
                    <div className="flex-shrink-0">
                      <svg className="h-6 w-6 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"></path>
                      </svg>
                    </div>
                    <div className="ml-3">
                      <p className="text-sm font-medium text-gray-900">Đường dây nóng</p>
                      <p className="text-sm text-gray-500">0123.456.789</p>
                    </div>
                  </div>
                  
                  <div className="flex items-center">
                    <div className="flex-shrink-0">
                      <svg className="h-6 w-6 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
                      </svg>
                    </div>
                    <div className="ml-3">
                      <p className="text-sm font-medium text-gray-900">Email hỗ trợ</p>
                      <p className="text-sm text-gray-500">hotro@diemdanh.edu.vn</p>
                    </div>
                  </div>
                  
                  <div className="flex items-center">
                    <div className="flex-shrink-0">
                      <svg className="h-6 w-6 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                      </svg>
                    </div>
                    <div className="ml-3">
                      <p className="text-sm font-medium text-gray-900">Giờ làm việc</p>
                      <p className="text-sm text-gray-500">Thứ 2 - Thứ 6: 8:00 - 17:00</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
  // System Settings Component
  function SystemSettings() {
    const [activeSection, setActiveSection] = useState('general');
  
    return (
      <div>
        <div className="mb-6 flex justify-between items-center">
          <h2 className="text-2xl font-bold text-gray-800">Cài đặt hệ thống</h2>
          <button className="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700 transition-colors">
            Lưu thay đổi
          </button>
        </div>
  
        <div className="bg-white rounded-lg shadow">
          <div className="sm:hidden">
            <select
              className="block w-full p-3 border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
              value={activeSection}
              onChange={(e) => setActiveSection(e.target.value)}
            >
              <option value="general">Cài đặt chung</option>
              <option value="attendance">Quy định điểm danh</option>
              <option value="notifications">Thông báo</option>
              <option value="security">Bảo mật</option>
            </select>
          </div>
  
          <div className="hidden sm:block">
            <div className="border-b border-gray-200">
              <nav className="flex -mb-px">
                <button
                  className={`py-4 px-6 text-sm font-medium ${
                    activeSection === 'general'
                      ? 'border-b-2 border-indigo-500 text-indigo-600'
                      : 'border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  }`}
                  onClick={() => setActiveSection('general')}
                >
                  Cài đặt chung
                </button>
                <button
                  className={`py-4 px-6 text-sm font-medium ${
                    activeSection === 'attendance'
                      ? 'border-b-2 border-indigo-500 text-indigo-600'
                      : 'border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  }`}
                  onClick={() => setActiveSection('attendance')}
                >
                  Quy định điểm danh
                </button>
                <button
                  className={`py-4 px-6 text-sm font-medium ${
                    activeSection === 'notifications'
                      ? 'border-b-2 border-indigo-500 text-indigo-600'
                      : 'border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  }`}
                  onClick={() => setActiveSection('notifications')}
                >
                  Thông báo
                </button>
                <button
                  className={`py-4 px-6 text-sm font-medium ${
                    activeSection === 'security'
                      ? 'border-b-2 border-indigo-500 text-indigo-600'
                      : 'border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  }`}
                  onClick={() => setActiveSection('security')}
                >
                  Bảo mật
                </button>
              </nav>
            </div>
          </div>
  
          <div className="p-6">
            {activeSection === 'general' && (
              <div>
                <h3 className="text-lg font-medium text-gray-900 mb-4">Cài đặt chung</h3>
                <div className="space-y-6">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tên trường</label>
                    <input
                      type="text"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="Đại học Công nghệ Sài Gòn"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Địa chỉ</label>
                    <input
                      type="text"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="180 Cao Lỗ, Phường 4, Quận 8, TP.HCM"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Múi giờ</label>
                    <select
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="Asia/Ho_Chi_Minh"
                    >
                      <option value="Asia/Ho_Chi_Minh">Asia/Ho_Chi_Minh (GMT+7)</option>
                      <option value="Asia/Bangkok">Asia/Bangkok (GMT+7)</option>
                      <option value="Asia/Singapore">Asia/Singapore (GMT+8)</option>
                    </select>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Logo trường</label>
                    <div className="flex items-center">
                      <div className="w-16 h-16 bg-gray-200 rounded-md flex items-center justify-center text-gray-400 mr-4">
                        Logo
                      </div>
                      <button className="bg-white text-gray-700 border border-gray-300 px-4 py-2 rounded-md hover:bg-gray-50 transition-colors">
                        Thay đổi
                      </button>
                    </div>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Ngôn ngữ mặc định</label>
                    <select
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="vi"
                    >
                      <option value="vi">Tiếng Việt</option>
                      <option value="en">English</option>
                    </select>
                  </div>
                </div>
              </div>
            )}
  
            {activeSection === 'attendance' && (
              <div>
                <h3 className="text-lg font-medium text-gray-900 mb-4">Quy định điểm danh</h3>
                <div className="space-y-6">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Thời gian điểm danh tối đa (phút)</label>
                    <input
                      type="number"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="15"
                    />
                    <p className="mt-1 text-sm text-gray-500">Sinh viên có thể điểm danh trong khoảng thời gian này sau khi giảng viên mở phiên điểm danh</p>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Số buổi vắng tối đa cho phép</label>
                    <input
                      type="number"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="3"
                    />
                    <p className="mt-1 text-sm text-gray-500">Số buổi vắng tối đa mà sinh viên được phép trong một học kỳ trước khi bị cấm thi</p>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Số buổi vắng để gửi cảnh báo</label>
                    <input
                      type="number"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="2"
                    />
                    <p className="mt-1 text-sm text-gray-500">Hệ thống sẽ gửi cảnh báo cho sinh viên khi số buổi vắng đạt đến ngưỡng này</p>
                  </div>
                  <div className="flex items-center">
                    <input
                      id="late-penalty"
                      type="checkbox"
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                      defaultChecked
                    />
                    <label htmlFor="late-penalty" className="ml-2 block text-sm text-gray-700">
                      Áp dụng hình phạt đi trễ (3 lần đi trễ = 1 buổi vắng)
                    </label>
                  </div>
                  <div className="flex items-center">
                    <input
                      id="auto-notification"
                      type="checkbox"
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                      defaultChecked
                    />
                    <label htmlFor="auto-notification" className="ml-2 block text-sm text-gray-700">
                      Tự động thông báo cho phụ huynh khi sinh viên vắng học
                    </label>
                  </div>
                  <div className="flex items-center">
                    <input
                      id="teacher-edit"
                      type="checkbox"
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                      defaultChecked
                    />
                    <label htmlFor="teacher-edit" className="ml-2 block text-sm text-gray-700">
                      Cho phép giảng viên chỉnh sửa điểm danh sau khi đã đóng phiên
                    </label>
                  </div>
                </div>
              </div>
            )}
  
            {activeSection === 'notifications' && (
              <div>
                <h3 className="text-lg font-medium text-gray-900 mb-4">Cài đặt thông báo</h3>
                <div className="space-y-6">
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 mb-3">Thông báo Email</h4>
                    <div className="space-y-2">
                      <div className="flex items-center justify-between py-2 border-b border-gray-200">
                        <div>
                          <p className="text-sm font-medium text-gray-700">Cảnh báo vắng học</p>
                          <p className="text-xs text-gray-500">Gửi email thông báo khi sinh viên vắng học</p>
                        </div>
                        <div className="relative inline-block w-10 mr-2 align-middle select-none">
                          <input type="checkbox" defaultChecked className="toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer" />
                          <label className="toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                        </div>
                      </div>
                      <div className="flex items-center justify-between py-2 border-b border-gray-200">
                        <div>
                          <p className="text-sm font-medium text-gray-700">Báo cáo hàng tuần</p>
                          <p className="text-xs text-gray-500">Gửi báo cáo tổng hợp điểm danh cho giảng viên</p>
                        </div>
                        <div className="relative inline-block w-10 mr-2 align-middle select-none">
                          <input type="checkbox" defaultChecked className="toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer" />
                          <label className="toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                        </div>
                      </div>
                      <div className="flex items-center justify-between py-2 border-b border-gray-200">
                        <div>
                          <p className="text-sm font-medium text-gray-700">Thông báo bảo trì</p>
                          <p className="text-xs text-gray-500">Thông báo khi hệ thống cần bảo trì</p>
                        </div>
                        <div className="relative inline-block w-10 mr-2 align-middle select-none">
                          <input type="checkbox" defaultChecked className="toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer" />
                          <label className="toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 mb-3">Thông báo SMS</h4>
                    <div className="space-y-2">
                      <div className="flex items-center justify-between py-2 border-b border-gray-200">
                        <div>
                          <p className="text-sm font-medium text-gray-700">Thông báo khẩn cấp</p>
                          <p className="text-xs text-gray-500">Gửi SMS cho các thông báo khẩn cấp</p>
                        </div>
                        <div className="relative inline-block w-10 mr-2 align-middle select-none">
                          <input type="checkbox" className="toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer" />
                          <label className="toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                        </div>
                      </div>
                      <div className="flex items-center justify-between py-2 border-b border-gray-200">
                        <div>
                          <p className="text-sm font-medium text-gray-700">Thông báo vắng học</p>
                          <p className="text-xs text-gray-500">Gửi SMS cho phụ huynh khi sinh viên vắng học</p>
                        </div>
                        <div className="relative inline-block w-10 mr-2 align-middle select-none">
                          <input type="checkbox" className="toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer" />
                          <label className="toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div>
                    <h4 className="text-sm font-medium text-gray-700 mb-1">Cấu hình máy chủ Email</h4>
                    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
                      <div>
                        <label className="block text-xs font-medium text-gray-700 mb-1">Máy chủ SMTP</label>
                        <input
                          type="text"
                          className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                          defaultValue="smtp.stu.edu.vn"
                        />
                      </div>
                      <div>
                        <label className="block text-xs font-medium text-gray-700 mb-1">Cổng</label>
                        <input
                          type="text"
                          className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                          defaultValue="587"
                        />
                      </div>
                      <div>
                        <label className="block text-xs font-medium text-gray-700 mb-1">Tên đăng nhập</label>
                        <input
                          type="text"
                          className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                          defaultValue="system@stu.edu.vn"
                        />
                      </div>
                      <div>
                        <label className="block text-xs font-medium text-gray-700 mb-1">Mật khẩu</label>
                        <input
                          type="password"
                          className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                          defaultValue="********"
                        />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            )}
  
            {activeSection === 'security' && (
              <div>
                <h3 className="text-lg font-medium text-gray-900 mb-4">Cài đặt bảo mật</h3>
                <div className="space-y-6">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Thời gian phiên đăng nhập (phút)</label>
                    <input
                      type="number"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="30"
                    />
                    <p className="mt-1 text-sm text-gray-500">Phiên làm việc sẽ tự động đăng xuất sau khoảng thời gian này nếu không hoạt động</p>
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Độ dài mật khẩu tối thiểu</label>
                    <input
                      type="number"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="8"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Số lần đăng nhập thất bại tối đa</label>
                    <input
                      type="number"
                      className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                      defaultValue="5"
                    />
                    <p className="mt-1 text-sm text-gray-500">Tài khoản sẽ bị khóa tạm thời sau số lần đăng nhập thất bại này</p>
                  </div>
                  <div className="flex items-center">
                    <input
                      id="two-factor"
                      type="checkbox"
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                    />
                    <label htmlFor="two-factor" className="ml-2 block text-sm text-gray-700">
                      Bật xác thực hai yếu tố cho tài khoản quản trị viên
                    </label>
                  </div>
                  <div className="flex items-center">
                    <input
                      id="password-expiry"
                      type="checkbox"
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                      defaultChecked
                    />
                    <label htmlFor="password-expiry" className="ml-2 block text-sm text-gray-700">
                      Yêu cầu đổi mật khẩu định kỳ (90 ngày)
                    </label>
                  </div>
                  <div className="flex items-center">
                    <input
                      id="ip-restriction"
                      type="checkbox"
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                    />
                    <label htmlFor="ip-restriction" className="ml-2 block text-sm text-gray-700">
                      Giới hạn đăng nhập theo địa chỉ IP
                    </label>
                  </div>
                  <div>
                    <button className="bg-red-100 text-red-700 px-4 py-2 rounded-md hover:bg-red-200 transition-colors">
                      Đặt lại tất cả mật khẩu
                    </button>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    );
  }