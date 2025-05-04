import { useState } from 'react';
import { Calendar, Clock, Users, User, Bell, AlertTriangle, Briefcase, FileText, Settings, Search, Home, LogOut, Menu, X } from 'lucide-react';

export default function TrainingDepartmentDashboard() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [notificationCount] = useState(5);

  const handleTabChange = (tab) => {
    setActiveTab(tab);
    if (window.innerWidth < 768) {
      setSidebarOpen(false);
    }
  };

  // Mock data
  const announcements = [
    { id: 1, title: 'Thông báo lịch thi học kỳ 2', date: '02/05/2025', priority: 'high' },
    { id: 2, title: 'Thay đổi phòng học môn Lập trình Web', date: '01/05/2025', priority: 'medium' },
    { id: 3, title: 'Điều chỉnh thời khóa biểu tuần 18', date: '30/04/2025', priority: 'low' },
  ];

  const absenceWarnings = [
    { id: 1, studentId: 'SV001', name: 'Nguyễn Văn A', subject: 'Lập trình Web', absences: 4, maxAbsences: 5 },
    { id: 2, studentId: 'SV002', name: 'Trần Thị B', subject: 'Cơ sở dữ liệu', absences: 3, maxAbsences: 3 },
    { id: 3, studentId: 'SV003', name: 'Lê Minh C', subject: 'Trí tuệ nhân tạo', absences: 5, maxAbsences: 5 },
  ];

  const scheduleChanges = [
    { id: 1, subject: 'Lập trình Web', oldRoom: 'A203', newRoom: 'B305', date: '06/05/2025' },
    { id: 2, subject: 'Mạng máy tính', oldRoom: 'B201', newRoom: 'A102', date: '07/05/2025' },
  ];

  // Render content based on active tab
  const renderContent = () => {
    switch (activeTab) {
      case 'dashboard':
        return <Dashboard announcements={announcements} absenceWarnings={absenceWarnings} scheduleChanges={scheduleChanges} />;
      case 'schedule':
        return <ScheduleManagement />;
      case 'rooms':
        return <RoomManagement />;
      case 'students':
        return <StudentManagement />;
      case 'teachers':
        return <TeacherManagement />;
      case 'announcements':
        return <AnnouncementManagement announcements={announcements} />;
      case 'warnings':
        return <AbsenceWarnings absenceWarnings={absenceWarnings} />;
      default:
        return <Dashboard />;
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
      <div className={`fixed inset-y-0 left-0 w-64 bg-blue-800 text-white transform transition-transform duration-300 ease-in-out z-30 ${sidebarOpen ? 'translate-x-0' : '-translate-x-full md:translate-x-0'}`}>
        <div className="p-4">
          <div className="flex items-center mb-8">
            <img src="/api/placeholder/40/40" alt="logo" className="w-10 h-10 rounded-full mr-3" />
            <div>
              <h2 className="font-bold text-lg">Đại học</h2>
              <h3 className="text-sm font-medium">Công nghệ Sài Gòn</h3>
            </div>
          </div>

          <nav className="space-y-1">
            <button 
              onClick={() => handleTabChange('dashboard')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'dashboard' ? 'bg-blue-900' : 'hover:bg-blue-700'}`}
            >
              <Home size={18} className="mr-3" />
              <span>Tổng quan</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('schedule')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'schedule' ? 'bg-blue-900' : 'hover:bg-blue-700'}`}
            >
              <Calendar size={18} className="mr-3" />
              <span>Quản lý lịch học</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('rooms')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'rooms' ? 'bg-blue-900' : 'hover:bg-blue-700'}`}
            >
              <Briefcase size={18} className="mr-3" />
              <span>Quản lý phòng học</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('students')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'students' ? 'bg-blue-900' : 'hover:bg-blue-700'}`}
            >
              <Users size={18} className="mr-3" />
              <span>Quản lý sinh viên</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('teachers')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'teachers' ? 'bg-blue-900' : 'hover:bg-blue-700'}`}
            >
              <User size={18} className="mr-3" />
              <span>Quản lý giảng viên</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('announcements')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'announcements' ? 'bg-blue-900' : 'hover:bg-blue-700'}`}
            >
              <Bell size={18} className="mr-3" />
              <span>Gửi thông báo</span>
            </button>
            
            <button 
              onClick={() => handleTabChange('warnings')} 
              className={`flex items-center px-4 py-3 w-full rounded ${activeTab === 'warnings' ? 'bg-blue-900' : 'hover:bg-blue-700'}`}
            >
              <AlertTriangle size={18} className="mr-3" />
              <span>Cảnh báo học vụ</span>
            </button>
          </nav>

          <div className="absolute bottom-0 left-0 right-0 p-4">
            <button className="flex items-center px-4 py-3 w-full rounded hover:bg-blue-700 text-white">
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
          <h1 className="text-xl font-semibold text-gray-800">{getPageTitle(activeTab)}</h1>
          
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

// Helper function to get page title
function getPageTitle(tab) {
  switch (tab) {
    case 'dashboard': return 'Tổng quan';
    case 'schedule': return 'Quản lý lịch học';
    case 'rooms': return 'Quản lý phòng học';
    case 'students': return 'Quản lý thông tin sinh viên';
    case 'teachers': return 'Quản lý thông tin giảng viên';
    case 'announcements': return 'Gửi thông báo';
    case 'warnings': return 'Cảnh báo học vụ';
    default: return 'Tổng quan';
  }
}

// Dashboard component
function Dashboard({ announcements, absenceWarnings, scheduleChanges }) {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      {/* Stats */}
      <div className="col-span-1 md:col-span-2 lg:col-span-3 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <div className="bg-white rounded-lg shadow p-4 flex items-center">
          <div className="rounded-full bg-blue-100 p-3 mr-4">
            <Users size={24} className="text-blue-600" />
          </div>
          <div>
            <h3 className="text-sm font-medium text-gray-500">Tổng sinh viên</h3>
            <p className="text-2xl font-bold">5,280</p>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-4 flex items-center">
          <div className="rounded-full bg-green-100 p-3 mr-4">
            <User size={24} className="text-green-600" />
          </div>
          <div>
            <h3 className="text-sm font-medium text-gray-500">Tổng giảng viên</h3>
            <p className="text-2xl font-bold">142</p>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-4 flex items-center">
          <div className="rounded-full bg-purple-100 p-3 mr-4">
            <Briefcase size={24} className="text-purple-600" />
          </div>
          <div>
            <h3 className="text-sm font-medium text-gray-500">Phòng học</h3>
            <p className="text-2xl font-bold">48</p>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-4 flex items-center">
          <div className="rounded-full bg-red-100 p-3 mr-4">
            <AlertTriangle size={24} className="text-red-600" />
          </div>
          <div>
            <h3 className="text-sm font-medium text-gray-500">Cảnh báo</h3>
            <p className="text-2xl font-bold">{absenceWarnings?.length || 0}</p>
          </div>
        </div>
      </div>
      
      {/* Recent announcements */}
      <div className="bg-white rounded-lg shadow">
        <div className="border-b px-6 py-3">
          <h2 className="font-semibold text-gray-800">Thông báo gần đây</h2>
        </div>
        <div className="p-4">
          {announcements?.length > 0 ? (
            <ul className="divide-y divide-gray-200">
              {announcements.map(announcement => (
                <li key={announcement.id} className="py-3">
                  <div className="flex items-start">
                    <div className={`mt-1 h-2 w-2 rounded-full mr-3 ${
                      announcement.priority === 'high' ? 'bg-red-500' : 
                      announcement.priority === 'medium' ? 'bg-yellow-500' : 'bg-green-500'
                    }`}></div>
                    <div>
                      <p className="font-medium text-gray-800">{announcement.title}</p>
                      <p className="text-sm text-gray-500">{announcement.date}</p>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-500 text-center py-4">Không có thông báo mới</p>
          )}
        </div>
      </div>
      
      {/* Absence warnings */}
      <div className="bg-white rounded-lg shadow">
        <div className="border-b px-6 py-3">
          <h2 className="font-semibold text-gray-800">Cảnh báo vắng học</h2>
        </div>
        <div className="p-4">
          {absenceWarnings?.length > 0 ? (
            <ul className="divide-y divide-gray-200">
              {absenceWarnings.map(warning => (
                <li key={warning.id} className="py-3">
                  <div className="flex flex-col">
                    <div className="flex justify-between">
                      <p className="font-medium text-gray-800">{warning.name}</p>
                      <span className={`text-sm font-medium ${
                        warning.absences >= warning.maxAbsences ? 'text-red-600' : 'text-yellow-600'
                      }`}>
                        {warning.absences}/{warning.maxAbsences}
                      </span>
                    </div>
                    <div className="flex justify-between text-sm text-gray-500">
                      <span>{warning.studentId}</span>
                      <span>{warning.subject}</span>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-500 text-center py-4">Không có cảnh báo</p>
          )}
        </div>
      </div>
      
      {/* Schedule changes */}
      <div className="bg-white rounded-lg shadow">
        <div className="border-b px-6 py-3">
          <h2 className="font-semibold text-gray-800">Thay đổi lịch học</h2>
        </div>
        <div className="p-4">
          {scheduleChanges?.length > 0 ? (
            <ul className="divide-y divide-gray-200">
              {scheduleChanges.map(change => (
                <li key={change.id} className="py-3">
                  <div className="flex flex-col">
                    <p className="font-medium text-gray-800">{change.subject}</p>
                    <div className="flex text-sm text-gray-500 mt-1">
                      <span>Phòng: {change.oldRoom} → {change.newRoom}</span>
                    </div>
                    <div className="text-sm text-gray-500 mt-1">
                      <span>Ngày: {change.date}</span>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-500 text-center py-4">Không có thay đổi mới</p>
          )}
        </div>
      </div>
    </div>
  );
}

// Schedule Management component
function ScheduleManagement() {
  const [viewMode, setViewMode] = useState('week');
  const [selectedClass, setSelectedClass] = useState('');
  
  // Mock data
  const classes = ['IT001', 'IT002', 'CS001', 'CS002', 'MA001'];
  
  return (
    <div className="space-y-6">
      <div className="bg-white rounded-lg shadow p-4">
        <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-4">
          <h2 className="text-lg font-semibold text-gray-800 mb-2 sm:mb-0">Thời khóa biểu</h2>
          
          <div className="flex space-x-2">
            <select 
              value={selectedClass} 
              onChange={(e) => setSelectedClass(e.target.value)}
              className="border rounded px-3 py-1 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">Tất cả lớp</option>
              {classes.map(cls => (
                <option key={cls} value={cls}>{cls}</option>
              ))}
            </select>
            
            <div className="flex border rounded">
              <button 
                onClick={() => setViewMode('day')} 
                className={`px-3 py-1 ${viewMode === 'day' ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}
              >
                Ngày
              </button>
              <button 
                onClick={() => setViewMode('week')} 
                className={`px-3 py-1 ${viewMode === 'week' ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}
              >
                Tuần
              </button>
              <button 
                onClick={() => setViewMode('month')} 
                className={`px-3 py-1 ${viewMode === 'month' ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}
              >
                Tháng
              </button>
            </div>
          </div>
        </div>

        {/* Calendar view */}
        <div className="border rounded">
          <div className="grid grid-cols-8 border-b text-center">
            <div className="py-2 border-r"></div>
            <div className="py-2 border-r font-medium">T2</div>
            <div className="py-2 border-r font-medium">T3</div>
            <div className="py-2 border-r font-medium">T4</div>
            <div className="py-2 border-r font-medium">T5</div>
            <div className="py-2 border-r font-medium">T6</div>
            <div className="py-2 border-r font-medium">T7</div>
            <div className="py-2 font-medium">CN</div>
          </div>
          
          {/* Calendar rows */}
          {Array.from({ length: 6 }).map((_, i) => (
            <div key={i} className="grid grid-cols-8 border-b last:border-b-0" style={{ height: '80px' }}>
              <div className="border-r p-2 flex items-center justify-center text-gray-500 text-sm">
                {7 + i}:00
              </div>
              {Array.from({ length: 7 }).map((_, j) => {
                // Randomly place some classes for demo purposes
                const hasClass = Math.random() > 0.8;
                return (
                  <div key={j} className="border-r last:border-r-0 p-1 relative">
                    {hasClass && (
                      <div className="absolute inset-1 bg-blue-100 border border-blue-300 rounded p-1 text-xs overflow-hidden">
                        <div className="font-medium">IT101</div>
                        <div>P.A205</div>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          ))}
        </div>
      </div>

      <div className="bg-white rounded-lg shadow p-4">
        <h2 className="text-lg font-semibold text-gray-800 mb-4">Điều chỉnh thời khóa biểu</h2>
        
        <div className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Lớp học phần</label>
              <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="">Chọn lớp học phần</option>
                {classes.map(cls => (
                  <option key={cls} value={cls}>{cls}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Phòng học</label>
              <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="">Chọn phòng học</option>
                <option value="A101">A101</option>
                <option value="A102">A102</option>
                <option value="B201">B201</option>
                <option value="B202">B202</option>
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Giảng viên</label>
              <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="">Chọn giảng viên</option>
                <option value="GV001">Nguyễn Văn X</option>
                <option value="GV002">Trần Thị Y</option>
                <option value="GV003">Lê Minh Z</option>
              </select>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Ngày học</label>
              <input type="date" className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Thời gian bắt đầu</label>
              <input type="time" className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Thời gian kết thúc</label>
              <input type="time" className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
            </div>
            
            <div className="flex items-end">
              <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 w-full">Cập nhật</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
// Room Management component
function RoomManagement() {
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedBuilding, setSelectedBuilding] = useState('all');
    const [selectedFloor, setSelectedFloor] = useState('all');
    const [selectedType, setSelectedType] = useState('all');
    const [showAddModal, setShowAddModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);
    const [currentRoom, setCurrentRoom] = useState(null);
    
    // Mock data
    const buildings = ['A', 'B', 'C', 'D'];
    const floors = ['1', '2', '3', '4', '5'];
    const roomTypes = ['Phòng học', 'Phòng lab', 'Phòng hội thảo', 'Phòng máy tính'];
    
    const rooms = [
      { id: 1, code: 'A101', name: 'Phòng học A101', building: 'A', floor: '1', type: 'Phòng học', capacity: 40, facilities: ['Máy chiếu', 'Máy lạnh', 'Bảng phấn'], status: 'available' },
      { id: 2, code: 'A102', name: 'Phòng học A102', building: 'A', floor: '1', type: 'Phòng học', capacity: 60, facilities: ['Máy chiếu', 'Máy lạnh', 'Bảng từ'], status: 'available' },
      { id: 3, code: 'B201', name: 'Phòng lab B201', building: 'B', floor: '2', type: 'Phòng lab', capacity: 30, facilities: ['Máy chiếu', 'Máy lạnh', 'Thiết bị thí nghiệm'], status: 'maintenance' },
      { id: 4, code: 'B301', name: 'Phòng máy tính B301', building: 'B', floor: '3', type: 'Phòng máy tính', capacity: 35, facilities: ['Máy chiếu', 'Máy lạnh', '35 máy tính'], status: 'occupied' },
      { id: 5, code: 'C101', name: 'Phòng hội thảo C101', building: 'C', floor: '1', type: 'Phòng hội thảo', capacity: 80, facilities: ['Hệ thống âm thanh', 'Máy chiếu', 'Máy lạnh'], status: 'available' },
      { id: 6, code: 'A202', name: 'Phòng học A202', building: 'A', floor: '2', type: 'Phòng học', capacity: 50, facilities: ['Máy chiếu', 'Máy lạnh', 'Bảng từ'], status: 'available' },
      { id: 7, code: 'D101', name: 'Phòng học D101', building: 'D', floor: '1', type: 'Phòng học', capacity: 45, facilities: ['Máy chiếu', 'Máy lạnh', 'Bảng từ'], status: 'maintenance' },
      { id: 8, code: 'B202', name: 'Phòng lab B202', building: 'B', floor: '2', type: 'Phòng lab', capacity: 25, facilities: ['Máy chiếu', 'Máy lạnh', 'Thiết bị thí nghiệm'], status: 'available' },
    ];
  
    // Filter rooms based on search term and filters
    const filteredRooms = rooms.filter(room => {
      const matchesSearch = room.code.toLowerCase().includes(searchTerm.toLowerCase()) || 
                           room.name.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesBuilding = selectedBuilding === 'all' || room.building === selectedBuilding;
      const matchesFloor = selectedFloor === 'all' || room.floor === selectedFloor;
      const matchesType = selectedType === 'all' || room.type === selectedType;
      
      return matchesSearch && matchesBuilding && matchesFloor && matchesType;
    });
  
    // Handle room editing
    const handleEditRoom = (room) => {
      setCurrentRoom(room);
      setShowEditModal(true);
    };
  
    // Handle room deletion
    const handleDeleteRoom = (roomId) => {
      // In a real application, this would be an API call
      alert(`Xóa phòng học ID: ${roomId}`);
    };
  
    // Add new room
    const handleAddNewRoom = () => {
      setShowAddModal(true);
    };
  
    // Get status color
    const getStatusColor = (status) => {
      switch (status) {
        case 'available': return 'bg-green-100 text-green-800';
        case 'occupied': return 'bg-blue-100 text-blue-800';
        case 'maintenance': return 'bg-yellow-100 text-yellow-800';
        default: return 'bg-gray-100 text-gray-800';
      }
    };
  
    // Get status text
    const getStatusText = (status) => {
      switch (status) {
        case 'available': return 'Trống';
        case 'occupied': return 'Đang sử dụng';
        case 'maintenance': return 'Bảo trì';
        default: return status;
      }
    };
    
    return (
      <div className="space-y-6">
        {/* Filters and search */}
        <div className="bg-white rounded-lg shadow p-4">
          <div className="mb-4 flex flex-col md:flex-row md:items-center md:justify-between">
            <h2 className="text-lg font-semibold text-gray-800 mb-2 md:mb-0">Danh sách phòng học</h2>
            
            <div className="flex flex-col md:flex-row space-y-2 md:space-y-0 md:space-x-2">
              <div className="relative">
                <input 
                  type="text" 
                  placeholder="Tìm kiếm phòng học..." 
                  className="border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 pl-8"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <Search size={16} className="absolute left-2 top-3 text-gray-400" />
              </div>
              
              <button 
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 flex items-center justify-center"
                onClick={handleAddNewRoom}
              >
                <span>+ Thêm phòng mới</span>
              </button>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Tòa nhà</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedBuilding}
                onChange={(e) => setSelectedBuilding(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {buildings.map(building => (
                  <option key={building} value={building}>Tòa {building}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Tầng</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedFloor}
                onChange={(e) => setSelectedFloor(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {floors.map(floor => (
                  <option key={floor} value={floor}>Tầng {floor}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Loại phòng</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedType}
                onChange={(e) => setSelectedType(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {roomTypes.map(type => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
              <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="all">Tất cả</option>
                <option value="available">Trống</option>
                <option value="occupied">Đang sử dụng</option>
                <option value="maintenance">Bảo trì</option>
              </select>
            </div>
          </div>
        </div>
        
        {/* Room list */}
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mã phòng</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Tên phòng</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Vị trí</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Loại</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Sức chứa</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Trạng thái</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredRooms.map(room => (
                  <tr key={room.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{room.code}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{room.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">Tòa {room.building}, Tầng {room.floor}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{room.type}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{room.capacity} người</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(room.status)}`}>
                        {getStatusText(room.status)}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <div className="flex space-x-2">
                        <button 
                          className="text-blue-600 hover:text-blue-900"
                          onClick={() => handleEditRoom(room)}
                        >
                          Sửa
                        </button>
                        <button 
                          className="text-red-600 hover:text-red-900"
                          onClick={() => handleDeleteRoom(room.id)}
                        >
                          Xóa
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          {filteredRooms.length === 0 && (
            <div className="text-center py-4 text-gray-500">
              Không tìm thấy phòng nào phù hợp với bộ lọc
            </div>
          )}
        </div>
        
        {/* Room statistics */}
        <div className="bg-white rounded-lg shadow p-4">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">Thống kê phòng học</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="p-4 bg-blue-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-blue-800">Phòng đang trống</h3>
                <span className="text-2xl font-bold text-blue-600">
                  {rooms.filter(room => room.status === 'available').length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-yellow-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-yellow-800">Phòng đang bảo trì</h3>
                <span className="text-2xl font-bold text-yellow-600">
                  {rooms.filter(room => room.status === 'maintenance').length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-green-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-green-800">Tổng sức chứa</h3>
                <span className="text-2xl font-bold text-green-600">
                  {rooms.reduce((total, room) => total + room.capacity, 0)} người
                </span>
              </div>
            </div>
          </div>
        </div>
        
        {/* Add Room Modal */}
        {showAddModal && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-md p-6 rounded-lg shadow-lg">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Thêm phòng mới</h3>
                <button onClick={() => setShowAddModal(false)}>
                  <X size={20} />
                </button>
              </div>
              
              <div className="space-y-4">
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Mã phòng</label>
                    <input type="text" className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tên phòng</label>
                    <input type="text" className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                  </div>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tòa nhà</label>
                    <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                      {buildings.map(building => (
                        <option key={building} value={building}>Tòa {building}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tầng</label>
                    <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                      {floors.map(floor => (
                        <option key={floor} value={floor}>Tầng {floor}</option>
                      ))}
                    </select>
                  </div>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Loại phòng</label>
                    <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                      {roomTypes.map(type => (
                        <option key={type} value={type}>{type}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Sức chứa</label>
                    <input type="number" className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                  </div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Trang thiết bị</label>
                  <div className="space-y-2">
                    <div className="flex items-center">
                      <input type="checkbox" id="projector" className="mr-2" />
                      <label htmlFor="projector">Máy chiếu</label>
                    </div>
                    <div className="flex items-center">
                      <input type="checkbox" id="ac" className="mr-2" />
                      <label htmlFor="ac">Máy lạnh</label>
                    </div>
                    <div className="flex items-center">
                      <input type="checkbox" id="whiteboard" className="mr-2" />
                      <label htmlFor="whiteboard">Bảng từ</label>
                    </div>
                    <div className="flex items-center">
                      <input type="checkbox" id="computer" className="mr-2" />
                      <label htmlFor="computer">Máy tính</label>
                    </div>
                  </div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="available">Trống</option>
                    <option value="occupied">Đang sử dụng</option>
                    <option value="maintenance">Bảo trì</option>
                  </select>
                </div>
                
                <div className="flex justify-end space-x-2 pt-4">
                  <button 
                    className="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300"
                    onClick={() => setShowAddModal(false)}
                  >
                    Hủy
                  </button>
                  <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                    Thêm phòng
                  </button>
                </div>
              </div>
            </div>
          </div>
        )}
        
        {/* Edit Room Modal */}
        {showEditModal && currentRoom && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-md p-6 rounded-lg shadow-lg">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Chỉnh sửa phòng {currentRoom.code}</h3>
                <button onClick={() => setShowEditModal(false)}>
                  <X size={20} />
                </button>
              </div>
              
              <div className="space-y-4">
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Mã phòng</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                      value={currentRoom.code}
                      readOnly
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tên phòng</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                      defaultValue={currentRoom.name}
                    />
                  </div>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tòa nhà</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={currentRoom.building}
                    >
                      {buildings.map(building => (
                        <option key={building} value={building}>Tòa {building}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Tầng</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={currentRoom.floor}
                    >
                      {floors.map(floor => (
                        <option key={floor} value={floor}>Tầng {floor}</option>
                      ))}
                    </select>
                  </div>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Loại phòng</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={currentRoom.type}
                    >
                      {roomTypes.map(type => (
                        <option key={type} value={type}>{type}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Sức chứa</label>
                    <input 
                      type="number" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                      defaultValue={currentRoom.capacity}
                    />
                  </div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Trang thiết bị</label>
                  <div className="space-y-2">
                    <div className="flex items-center">
                      <input 
                        type="checkbox" 
                        id="edit-projector" 
                        className="mr-2" 
                        defaultChecked={currentRoom.facilities.includes('Máy chiếu')}
                      />
                      <label htmlFor="edit-projector">Máy chiếu</label>
                    </div>
                    <div className="flex items-center">
                      <input 
                        type="checkbox" 
                        id="edit-ac" 
                        className="mr-2" 
                        defaultChecked={currentRoom.facilities.includes('Máy lạnh')}
                      />
                      <label htmlFor="edit-ac">Máy lạnh</label>
                    </div>
                    <div className="flex items-center">
                      <input 
                        type="checkbox" 
                        id="edit-whiteboard" 
                        className="mr-2" 
                        defaultChecked={currentRoom.facilities.includes('Bảng từ')}
                      />
                      <label htmlFor="edit-whiteboard">Bảng từ</label>
                    </div>
                    <div className="flex items-center">
                      <input 
                        type="checkbox" 
                        id="edit-computer" 
                        className="mr-2" 
                        defaultChecked={currentRoom.facilities.includes('Máy tính')}
                      />
                      <label htmlFor="edit-computer">Máy tính</label>
                    </div>
                  </div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
                  <select 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    defaultValue={currentRoom.status}
                  >
                    <option value="available">Trống</option>
                    <option value="occupied">Đang sử dụng</option>
                    <option value="maintenance">Bảo trì</option>
                  </select>
                </div>
                
                <div className="flex justify-end space-x-2 pt-4">
                  <button 
                    className="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300"
                    onClick={() => setShowEditModal(false)}
                  >
                    Hủy
                  </button>
                  <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                    Lưu thay đổi
                  </button>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    );
  }
  // Student Management component
function StudentManagement() {
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedDepartment, setSelectedDepartment] = useState('all');
    const [selectedYear, setSelectedYear] = useState('all');
    const [selectedClass, setSelectedClass] = useState('all');
    const [showAddModal, setShowAddModal] = useState(false);
    const [showDetailModal, setShowDetailModal] = useState(false);
    const [currentStudent, setCurrentStudent] = useState(null);
    const [selectedTab, setSelectedTab] = useState('info');
    
    // Mock data
    const departments = ['Công nghệ thông tin', 'Kinh tế', 'Ngoại ngữ', 'Điện - Điện tử'];
    const studyYears = ['1', '2', '3', '4'];
    const classes = ['CNTT1', 'CNTT2', 'KT1', 'KT2', 'NN1', 'DT1'];
    
    const students = [
      {
        id: 'SV001',
        name: 'Nguyễn Văn A',
        dob: '15/05/2003',
        gender: 'Nam',
        department: 'Công nghệ thông tin',
        class: 'CNTT1',
        year: '2',
        email: 'nguyenvana@student.edu.vn',
        phone: '0987654321',
        address: '123 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM',
        status: 'active',
        gpa: 3.5,
        attendance: 92,
        courses: [
          { code: 'IT101', name: 'Lập trình Web', credits: 3, grade: 'A', semester: 'HK1-2024' },
          { code: 'IT102', name: 'Cơ sở dữ liệu', credits: 4, grade: 'B+', semester: 'HK1-2024' },
          { code: 'IT103', name: 'Trí tuệ nhân tạo', credits: 3, grade: 'B', semester: 'HK1-2024' },
        ]
      },
      {
        id: 'SV002',
        name: 'Trần Thị B',
        dob: '20/02/2004',
        gender: 'Nữ',
        department: 'Kinh tế',
        class: 'KT1',
        year: '1',
        email: 'tranthib@student.edu.vn',
        phone: '0912345678',
        address: '456 Đường Lý Thường Kiệt, Quận 10, TP.HCM',
        status: 'active',
        gpa: 3.8,
        attendance: 95,
        courses: [
          { code: 'EC101', name: 'Kinh tế vĩ mô', credits: 3, grade: 'A', semester: 'HK1-2024' },
          { code: 'EC102', name: 'Kinh tế vi mô', credits: 3, grade: 'A-', semester: 'HK1-2024' },
          { code: 'MA101', name: 'Toán kinh tế', credits: 4, grade: 'B+', semester: 'HK1-2024' },
        ]
      },
      {
        id: 'SV003',
        name: 'Lê Minh C',
        dob: '10/11/2003',
        gender: 'Nam',
        department: 'Công nghệ thông tin',
        class: 'CNTT2',
        year: '2',
        email: 'leminhc@student.edu.vn',
        phone: '0978123456',
        address: '789 Đường Võ Văn Tần, Quận 3, TP.HCM',
        status: 'suspended',
        gpa: 2.3,
        attendance: 68,
        courses: [
          { code: 'IT101', name: 'Lập trình Web', credits: 3, grade: 'C', semester: 'HK1-2024' },
          { code: 'IT102', name: 'Cơ sở dữ liệu', credits: 4, grade: 'D', semester: 'HK1-2024' },
          { code: 'IT103', name: 'Trí tuệ nhân tạo', credits: 3, grade: 'C+', semester: 'HK1-2024' },
        ]
      },
      {
        id: 'SV004',
        name: 'Phạm Thị D',
        dob: '25/08/2002',
        gender: 'Nữ',
        department: 'Ngoại ngữ',
        class: 'NN1',
        year: '3',
        email: 'phamthid@student.edu.vn',
        phone: '0932156789',
        address: '101 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM',
        status: 'active',
        gpa: 3.2,
        attendance: 88,
        courses: [
          { code: 'EN201', name: 'Tiếng Anh thương mại', credits: 3, grade: 'B+', semester: 'HK1-2024' },
          { code: 'EN202', name: 'Biên phiên dịch', credits: 4, grade: 'A-', semester: 'HK1-2024' },
          { code: 'EN203', name: 'Ngôn ngữ học ứng dụng', credits: 3, grade: 'B', semester: 'HK1-2024' },
        ]
      },
      {
        id: 'SV005',
        name: 'Vũ Đức E',
        dob: '05/04/2004',
        gender: 'Nam',
        department: 'Điện - Điện tử',
        class: 'DT1',
        year: '1',
        email: 'vuducE@student.edu.vn',
        phone: '0965432109',
        address: '202 Đường Nguyễn Thị Minh Khai, Quận 1, TP.HCM',
        status: 'active',
        gpa: 3.0,
        attendance: 85,
        courses: [
          { code: 'EE101', name: 'Mạch điện tử', credits: 4, grade: 'B-', semester: 'HK1-2024' },
          { code: 'EE102', name: 'Kỹ thuật số', credits: 3, grade: 'B', semester: 'HK1-2024' },
          { code: 'PH101', name: 'Vật lý đại cương', credits: 4, grade: 'C+', semester: 'HK1-2024' },
        ]
      },
      {
        id: 'SV006',
        name: 'Đặng Hoàng F',
        dob: '12/12/2003',
        gender: 'Nam',
        department: 'Công nghệ thông tin',
        class: 'CNTT1',
        year: '2',
        email: 'danghoangf@student.edu.vn',
        phone: '0943215678',
        address: '303 Đường Cách Mạng Tháng 8, Quận 3, TP.HCM',
        status: 'active',
        gpa: 3.4,
        attendance: 90,
        courses: [
          { code: 'IT101', name: 'Lập trình Web', credits: 3, grade: 'A-', semester: 'HK1-2024' },
          { code: 'IT102', name: 'Cơ sở dữ liệu', credits: 4, grade: 'B', semester: 'HK1-2024' },
          { code: 'IT103', name: 'Trí tuệ nhân tạo', credits: 3, grade: 'B+', semester: 'HK1-2024' },
        ]
      },
      {
        id: 'SV007',
        name: 'Trương Thị G',
        dob: '30/06/2002',
        gender: 'Nữ',
        department: 'Kinh tế',
        class: 'KT2',
        year: '3',
        email: 'truongthig@student.edu.vn',
        phone: '0923456781',
        address: '404 Đường Nam Kỳ Khởi Nghĩa, Quận 3, TP.HCM',
        status: 'graduated',
        gpa: 3.9,
        attendance: 98,
        courses: [
          { code: 'EC201', name: 'Marketing cơ bản', credits: 3, grade: 'A', semester: 'HK1-2024' },
          { code: 'EC202', name: 'Quản trị doanh nghiệp', credits: 4, grade: 'A', semester: 'HK1-2024' },
          { code: 'EC203', name: 'Kế toán tài chính', credits: 3, grade: 'A-', semester: 'HK1-2024' },
        ]
      },
      {
        id: 'SV008',
        name: 'Ngô Văn H',
        dob: '18/03/2003',
        gender: 'Nam',
        department: 'Điện - Điện tử',
        class: 'DT1',
        year: '2',
        email: 'ngovanh@student.edu.vn',
        phone: '0934567812',
        address: '505 Đường Trần Hưng Đạo, Quận 1, TP.HCM',
        status: 'active',
        gpa: 2.8,
        attendance: 78,
        courses: [
          { code: 'EE103', name: 'Điều khiển tự động', credits: 4, grade: 'C+', semester: 'HK1-2024' },
          { code: 'EE104', name: 'Vi xử lý', credits: 3, grade: 'B-', semester: 'HK1-2024' },
          { code: 'EE105', name: 'Truyền thông không dây', credits: 3, grade: 'C', semester: 'HK1-2024' },
        ]
      },
    ];
  
    // Filter students based on search term and filters
    const filteredStudents = students.filter(student => {
      const matchesSearch = student.id.toLowerCase().includes(searchTerm.toLowerCase()) || 
                           student.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                           student.email.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesDepartment = selectedDepartment === 'all' || student.department === selectedDepartment;
      const matchesYear = selectedYear === 'all' || student.year === selectedYear;
      const matchesClass = selectedClass === 'all' || student.class === selectedClass;
      
      return matchesSearch && matchesDepartment && matchesYear && matchesClass;
    });
  
    // Handle student detail view
    const handleViewStudent = (student) => {
      setCurrentStudent(student);
      setShowDetailModal(true);
      setSelectedTab('info');
    };
  
    // Handle student deletion
    const handleDeleteStudent = (studentId) => {
      // In a real application, this would be an API call
      alert(`Xóa sinh viên ID: ${studentId}`);
    };
  
    // Add new student
    const handleAddNewStudent = () => {
      setShowAddModal(true);
    };
  
    // Get status color
    const getStatusColor = (status) => {
      switch (status) {
        case 'active': return 'bg-green-100 text-green-800';
        case 'suspended': return 'bg-red-100 text-red-800';
        case 'graduated': return 'bg-blue-100 text-blue-800';
        default: return 'bg-gray-100 text-gray-800';
      }
    };
  
    // Get status text
    const getStatusText = (status) => {
      switch (status) {
        case 'active': return 'Đang học';
        case 'suspended': return 'Đình chỉ';
        case 'graduated': return 'Đã tốt nghiệp';
        default: return status;
      }
    };
    
    return (
      <div className="space-y-6">
        {/* Filters and search */}
        <div className="bg-white rounded-lg shadow p-4">
          <div className="mb-4 flex flex-col md:flex-row md:items-center md:justify-between">
            <h2 className="text-lg font-semibold text-gray-800 mb-2 md:mb-0">Danh sách sinh viên</h2>
            
            <div className="flex flex-col md:flex-row space-y-2 md:space-y-0 md:space-x-2">
              <div className="relative">
                <input 
                  type="text" 
                  placeholder="Tìm kiếm sinh viên..." 
                  className="border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 pl-8"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <Search size={16} className="absolute left-2 top-3 text-gray-400" />
              </div>
              
              <button 
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 flex items-center justify-center"
                onClick={handleAddNewStudent}
              >
                <span>+ Thêm sinh viên mới</span>
              </button>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Khoa</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedDepartment}
                onChange={(e) => setSelectedDepartment(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {departments.map(department => (
                  <option key={department} value={department}>{department}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Năm học</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedYear}
                onChange={(e) => setSelectedYear(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {studyYears.map(year => (
                  <option key={year} value={year}>Năm {year}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Lớp</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedClass}
                onChange={(e) => setSelectedClass(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {classes.map(cls => (
                  <option key={cls} value={cls}>{cls}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
              <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="all">Tất cả</option>
                <option value="active">Đang học</option>
                <option value="suspended">Đình chỉ</option>
                <option value="graduated">Đã tốt nghiệp</option>
              </select>
            </div>
          </div>
        </div>
        
        {/* Student list */}
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mã SV</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Họ tên</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Khoa</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Lớp</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Năm</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Điểm TB</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Trạng thái</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredStudents.map(student => (
                  <tr key={student.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{student.id}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{student.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{student.department}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{student.class}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">Năm {student.year}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{student.gpa.toFixed(2)}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(student.status)}`}>
                        {getStatusText(student.status)}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <div className="flex space-x-2">
                        <button 
                          className="text-blue-600 hover:text-blue-900"
                          onClick={() => handleViewStudent(student)}
                        >
                          Xem
                        </button>
                        <button className="text-green-600 hover:text-green-900">
                          Sửa
                        </button>
                        <button 
                          className="text-red-600 hover:text-red-900"
                          onClick={() => handleDeleteStudent(student.id)}
                        >
                          Xóa
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          {filteredStudents.length === 0 && (
            <div className="text-center py-4 text-gray-500">
              Không tìm thấy sinh viên nào phù hợp với bộ lọc
            </div>
          )}
        </div>
        
        {/* Student statistics */}
        <div className="bg-white rounded-lg shadow p-4">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">Thống kê sinh viên</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="p-4 bg-blue-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-blue-800">Tổng sinh viên</h3>
                <span className="text-2xl font-bold text-blue-600">
                  {students.length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-green-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-green-800">Đang học</h3>
                <span className="text-2xl font-bold text-green-600">
                  {students.filter(student => student.status === 'active').length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-red-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-red-800">Đình chỉ</h3>
                <span className="text-2xl font-bold text-red-600">
                  {students.filter(student => student.status === 'suspended').length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-purple-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-purple-800">Điểm TB chung</h3>
                <span className="text-2xl font-bold text-purple-600">
                  {(students.reduce((acc, student) => acc + student.gpa, 0) / students.length).toFixed(2)}
                </span>
              </div>
            </div>
          </div>
        </div>
        
        {/* Student Detail Modal */}
        {showDetailModal && currentStudent && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Thông tin sinh viên: {currentStudent.name}</h3>
                <button onClick={() => setShowDetailModal(false)}>
                  <X size={20} />
                </button>
              </div>
              
              {/* Tabs */}
              <div className="border-b mb-4">
                <div className="flex space-x-4">
                  <button 
                    className={`pb-2 px-1 ${selectedTab === 'info' ? 'border-b-2 border-blue-500 text-blue-600' : 'text-gray-500'}`}
                    onClick={() => setSelectedTab('info')}
                  >
                    Thông tin cá nhân
                  </button>
                  <button 
                    className={`pb-2 px-1 ${selectedTab === 'academic' ? 'border-b-2 border-blue-500 text-blue-600' : 'text-gray-500'}`}
                    onClick={() => setSelectedTab('academic')}
                  >
                    Học tập
                  </button>
                  <button 
                    className={`pb-2 px-1 ${selectedTab === 'attendance' ? 'border-b-2 border-blue-500 text-blue-600' : 'text-gray-500'}`}
                    onClick={() => setSelectedTab('attendance')}
                  >
                    Điểm danh
                  </button>
                </div>
              </div>
              
              {/* Tab Content */}
              <div className="space-y-4">
                {selectedTab === 'info' && (
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <h4 className="font-medium text-gray-700 mb-3">Thông tin cá nhân</h4>
                      <div className="space-y-2">
                        <div className="flex">
                          <span className="w-32 text-gray-500">Mã sinh viên:</span>
                          <span className="font-medium">{currentStudent.id}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Họ và tên:</span>
                          <span className="font-medium">{currentStudent.name}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Ngày sinh:</span>
                          <span>{currentStudent.dob}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Giới tính:</span>
                          <span>{currentStudent.gender}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Email:</span>
                          <span>{currentStudent.email}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Số điện thoại:</span>
                          <span>{currentStudent.phone}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Địa chỉ:</span>
                          <span>{currentStudent.address}</span>
                        </div>
                      </div>
                    </div>
                    
                    <div>
                      <h4 className="font-medium text-gray-700 mb-3">Thông tin học tập</h4>
                      <div className="space-y-2">
                        <div className="flex">
                          <span className="w-32 text-gray-500">Khoa:</span>
                          <span>{currentStudent.department}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Lớp:</span>
                          <span>{currentStudent.class}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Năm học:</span>
                          <span>Năm {currentStudent.year}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Trạng thái:</span>
                          <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(currentStudent.status)}`}>
                            {getStatusText(currentStudent.status)}
                          </span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Điểm TB:</span>
                          <span className="font-medium">{currentStudent.gpa.toFixed(2)}</span>
                        </div>
                        <div className="flex">
                          <span className="w-32 text-gray-500">Tỷ lệ đi học:</span>
                          <span>{currentStudent.attendance}%</span>
                        </div>
                      </div>
                    </div>
                  </div>
                )}
                
                {selectedTab === 'academic' && (
                  <div>
                    <h4 className="font-medium text-gray-700 mb-3">Thông tin học phần</h4>
                    <div className="overflow-x-auto">
                      <table className="min-w-full divide-y divide-gray-200">
                        <thead className="bg-gray-50">
                          <tr>
                            <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mã HP</th>
                            <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Tên học phần</th>
                            <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Số tín chỉ</th>
                            <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Điểm</th>
                            <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Học kỳ</th>
                          </tr>
                        </thead>
                        <tbody className="bg-white divide-y divide-gray-200">
                          {currentStudent.courses.map((course, index) => (
                            <tr key={index} className="hover:bg-gray-50">
                              <td className="px-4 py-2 whitespace-nowrap text-sm font-medium text-gray-900">{course.code}</td>
                              <td className="px-4 py-2 whitespace-nowrap text-sm text-gray-500">{course.name}</td>
                            <td className="px-4 py-2 whitespace-nowrap text-sm text-gray-500">{course.credits}</td>
                            <td className="px-4 py-2 whitespace-nowrap text-sm text-gray-500">{course.grade}</td>
                            <td className="px-4 py-2 whitespace-nowrap text-sm text-gray-500">{course.semester}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                  
                  <div className="mt-6">
                    <h4 className="font-medium text-gray-700 mb-3">Biểu đồ điểm học tập</h4>
                    <div className="h-64 bg-gray-50 rounded border flex items-center justify-center">
                      {/* Placeholder for academic performance chart */}
                      <span className="text-gray-400">Biểu đồ điểm học tập</span>
                    </div>
                  </div>
                </div>
              )}
              
              {selectedTab === 'attendance' && (
                <div>
                  <h4 className="font-medium text-gray-700 mb-3">Thống kê điểm danh</h4>
                  
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
                    <div className="p-4 bg-green-50 rounded-lg">
                      <div className="flex justify-between items-center">
                        <h3 className="text-lg font-medium text-green-800">Tỷ lệ điểm danh</h3>
                        <span className="text-2xl font-bold text-green-600">
                          {currentStudent.attendance}%
                        </span>
                      </div>
                    </div>
                    
                    <div className="p-4 bg-red-50 rounded-lg">
                      <div className="flex justify-between items-center">
                        <h3 className="text-lg font-medium text-red-800">Buổi học vắng</h3>
                        <span className="text-2xl font-bold text-red-600">
                          {Math.round((100 - currentStudent.attendance) / 100 * 30)} / 30
                        </span>
                      </div>
                    </div>
                  </div>
                  
                  <div className="space-y-2">
                    <h4 className="font-medium text-gray-700 mb-3">Chi tiết điểm danh theo học phần</h4>
                    
                    {currentStudent.courses.map((course, index) => (
                      <div key={index} className="p-3 border rounded">
                        <div className="flex justify-between items-center mb-2">
                          <div>
                            <span className="font-medium">{course.code}: {course.name}</span>
                            <span className="text-sm text-gray-500 ml-2">({course.semester})</span>
                          </div>
                          <span className="text-sm font-medium">
                            Điểm danh: {80 + Math.floor(Math.random() * 20)}%
                          </span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2.5">
                          <div 
                            className="bg-blue-600 h-2.5 rounded-full" 
                            style={{ width: `${80 + Math.floor(Math.random() * 20)}%` }}
                          ></div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              )}
            </div>
            
            <div className="mt-6 flex justify-end">
              <button
                className="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300 mr-2"
                onClick={() => setShowDetailModal(false)}
              >
                Đóng
              </button>
              <button
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
              >
                Cập nhật
              </button>
            </div>
          </div>
        </div>
      )}
      
      {/* Add Student Modal */}
      {showAddModal && (
        <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
          <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-semibold">Thêm sinh viên mới</h3>
              <button onClick={() => setShowAddModal(false)}>
                <X size={20} />
              </button>
            </div>
            
            <form className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Mã sinh viên</label>
                  <input 
                    type="text" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                    placeholder="VD: SV001"
                  />
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Họ và tên</label>
                  <input 
                    type="text" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                    placeholder="VD: Nguyễn Văn A"
                  />
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ngày sinh</label>
                  <input 
                    type="date" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  />
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Giới tính</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">-- Chọn giới tính --</option>
                    <option value="Nam">Nam</option>
                    <option value="Nữ">Nữ</option>
                    <option value="Khác">Khác</option>
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                  <input 
                    type="email" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                    placeholder="VD: example@student.edu.vn"
                  />
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Số điện thoại</label>
                  <input 
                    type="tel" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                    placeholder="VD: 0987654321"
                  />
                </div>
                
                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Địa chỉ</label>
                  <input 
                    type="text" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                    placeholder="VD: 123 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM"
                  />
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Khoa</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">-- Chọn khoa --</option>
                    {departments.map(department => (
                      <option key={department} value={department}>{department}</option>
                    ))}
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Lớp</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">-- Chọn lớp --</option>
                    {classes.map(cls => (
                      <option key={cls} value={cls}>{cls}</option>
                    ))}
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Năm học</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">-- Chọn năm học --</option>
                    {studyYears.map(year => (
                      <option key={year} value={year}>Năm {year}</option>
                    ))}
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="active">Đang học</option>
                    <option value="suspended">Đình chỉ</option>
                    <option value="graduated">Đã tốt nghiệp</option>
                  </select>
                </div>
              </div>
              
              <div className="flex justify-end space-x-2">
                <button
                  type="button"
                  className="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300"
                  onClick={() => setShowAddModal(false)}
                >
                  Hủy
                </button>
                <button
                  type="submit"
                  className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                  Lưu sinh viên
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
// Teacher Management component
function TeacherManagement() {
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedDepartment, setSelectedDepartment] = useState('all');
    const [selectedPosition, setSelectedPosition] = useState('all');
    const [selectedSubject, setSelectedSubject] = useState('all');
    const [showAddModal, setShowAddModal] = useState(false);
    const [showDetailModal, setShowDetailModal] = useState(false);
    const [currentTeacher, setCurrentTeacher] = useState(null);
    const [selectedTab, setSelectedTab] = useState('info');
    
    // Mock data
    const departments = ['Công nghệ thông tin', 'Kinh tế', 'Ngoại ngữ', 'Điện - Điện tử'];
    const positions = ['Giảng viên', 'Trưởng khoa', 'Phó khoa', 'Trợ giảng', 'Thỉnh giảng'];
    const subjects = ['Lập trình Web', 'Cơ sở dữ liệu', 'Trí tuệ nhân tạo', 'Kinh tế vĩ mô', 'Kinh tế vi mô', 'Tiếng Anh thương mại'];
    
    const teachers = [
      {
        id: 'GV001',
        name: 'TS. Nguyễn Văn A',
        dob: '12/05/1975',
        gender: 'Nam',
        department: 'Công nghệ thông tin',
        position: 'Trưởng khoa',
        joinDate: '05/09/2010',
        email: 'nguyenvana@teacher.edu.vn',
        phone: '0987654321',
        address: '123 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM',
        status: 'active',
        degree: 'Tiến sĩ',
        specialization: 'Trí tuệ nhân tạo',
        workload: 32,
        subjects: [
          { code: 'IT101', name: 'Lập trình Web', credits: 3, classes: ['CNTT1', 'CNTT2'], semester: 'HK1-2024' },
          { code: 'IT103', name: 'Trí tuệ nhân tạo', credits: 3, classes: ['CNTT1'], semester: 'HK1-2024' },
        ],
        publications: [
          { title: 'Ứng dụng học máy trong phân tích dữ liệu lớn', year: 2022, journal: 'Tạp chí Công nghệ thông tin Việt Nam' },
          { title: 'Deep Learning cho nhận dạng hình ảnh', year: 2021, journal: 'Tạp chí Khoa học Đại học Quốc gia' }
        ]
      },
      {
        id: 'GV002',
        name: 'ThS. Trần Thị B',
        dob: '20/03/1985',
        gender: 'Nữ',
        department: 'Kinh tế',
        position: 'Giảng viên',
        joinDate: '10/02/2015',
        email: 'tranthib@teacher.edu.vn',
        phone: '0912345678',
        address: '456 Đường Lý Thường Kiệt, Quận 10, TP.HCM',
        status: 'active',
        degree: 'Thạc sĩ',
        specialization: 'Kinh tế vĩ mô',
        workload: 24,
        subjects: [
          { code: 'EC101', name: 'Kinh tế vĩ mô', credits: 3, classes: ['KT1', 'KT2'], semester: 'HK1-2024' },
          { code: 'EC102', name: 'Kinh tế vi mô', credits: 3, classes: ['KT1'], semester: 'HK1-2024' },
        ],
        publications: [
          { title: 'Phân tích các yếu tố ảnh hưởng đến tăng trưởng kinh tế Việt Nam', year: 2023, journal: 'Tạp chí Kinh tế và Phát triển' }
        ]
      },
      {
        id: 'GV003',
        name: 'TS. Lê Minh C',
        dob: '05/12/1980',
        gender: 'Nam',
        department: 'Công nghệ thông tin',
        position: 'Giảng viên',
        joinDate: '20/08/2012',
        email: 'leminhc@teacher.edu.vn',
        phone: '0978123456',
        address: '789 Đường Võ Văn Tần, Quận 3, TP.HCM',
        status: 'on_leave',
        degree: 'Tiến sĩ',
        specialization: 'Cơ sở dữ liệu',
        workload: 28,
        subjects: [
          { code: 'IT102', name: 'Cơ sở dữ liệu', credits: 4, classes: ['CNTT1', 'CNTT2'], semester: 'HK1-2024' },
        ],
        publications: [
          { title: 'Phương pháp tối ưu hóa truy vấn cơ sở dữ liệu phân tán', year: 2021, journal: 'Tạp chí Công nghệ thông tin và Truyền thông' },
          { title: 'NoSQL và ứng dụng trong xử lý dữ liệu lớn', year: 2020, journal: 'Tạp chí Khoa học Công nghệ' }
        ]
      },
      {
        id: 'GV004',
        name: 'TS. Phạm Thị D',
        dob: '25/07/1970',
        gender: 'Nữ',
        department: 'Ngoại ngữ',
        position: 'Trưởng khoa',
        joinDate: '15/10/2008',
        email: 'phamthid@teacher.edu.vn',
        phone: '0932156789',
        address: '101 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM',
        status: 'active',
        degree: 'Tiến sĩ',
        specialization: 'Ngôn ngữ học ứng dụng',
        workload: 18,
        subjects: [
          { code: 'EN201', name: 'Tiếng Anh thương mại', credits: 3, classes: ['NN1'], semester: 'HK1-2024' },
          { code: 'EN203', name: 'Ngôn ngữ học ứng dụng', credits: 3, classes: ['NN1'], semester: 'HK1-2024' },
        ],
        publications: [
          { title: 'Phương pháp giảng dạy tiếng Anh chuyên ngành Kinh tế', year: 2022, journal: 'Tạp chí Giáo dục' },
          { title: 'Ứng dụng công nghệ trong giảng dạy ngoại ngữ', year: 2021, journal: 'Tạp chí Khoa học Giáo dục' },
          { title: 'Phân tích đối chiếu cấu trúc ngữ pháp Anh-Việt', year: 2019, journal: 'Tạp chí Ngôn ngữ học' }
        ]
      },
      {
        id: 'GV005',
        name: 'ThS. Vũ Đức E',
        dob: '08/09/1990',
        gender: 'Nam',
        department: 'Điện - Điện tử',
        position: 'Giảng viên',
        joinDate: '01/09/2018',
        email: 'vuducE@teacher.edu.vn',
        phone: '0965432109',
        address: '202 Đường Nguyễn Thị Minh Khai, Quận 1, TP.HCM',
        status: 'active',
        degree: 'Thạc sĩ',
        specialization: 'Kỹ thuật điện tử',
        workload: 30,
        subjects: [
          { code: 'EE101', name: 'Mạch điện tử', credits: 4, classes: ['DT1'], semester: 'HK1-2024' },
          { code: 'EE102', name: 'Kỹ thuật số', credits: 3, classes: ['DT1'], semester: 'HK1-2024' },
        ],
        publications: []
      },
      {
        id: 'GV006',
        name: 'PGS.TS. Đặng Hoàng F',
        dob: '12/04/1965',
        gender: 'Nam',
        department: 'Công nghệ thông tin',
        position: 'Phó khoa',
        joinDate: '20/02/2005',
        email: 'danghoangf@teacher.edu.vn',
        phone: '0943215678',
        address: '303 Đường Cách Mạng Tháng 8, Quận 3, TP.HCM',
        status: 'active',
        degree: 'Phó Giáo sư',
        specialization: 'Công nghệ phần mềm',
        workload: 22,
        subjects: [
          { code: 'IT201', name: 'Công nghệ phần mềm', credits: 4, classes: ['CNTT1'], semester: 'HK1-2024' },
          { code: 'IT202', name: 'Kiểm thử phần mềm', credits: 3, classes: ['CNTT2'], semester: 'HK1-2024' },
        ],
        publications: [
          { title: 'Phương pháp phát triển phần mềm Agile trong các dự án lớn', year: 2022, journal: 'Tạp chí Khoa học Máy tính' },
          { title: 'DevOps và CI/CD: Xu hướng hiện đại trong phát triển phần mềm', year: 2021, journal: 'Tạp chí Công nghệ thông tin' },
          { title: 'Mô hình quản lý chất lượng phần mềm theo tiêu chuẩn ISO', year: 2020, journal: 'Tạp chí Khoa học và Công nghệ' }
        ]
      },
      {
        id: 'GV007',
        name: 'ThS. Trương Thị G',
        dob: '30/10/1988',
        gender: 'Nữ',
        department: 'Kinh tế',
        position: 'Giảng viên',
        joinDate: '15/03/2016',
        email: 'truongthig@teacher.edu.vn',
        phone: '0923456781',
        address: '404 Đường Nam Kỳ Khởi Nghĩa, Quận 3, TP.HCM',
        status: 'retired',
        degree: 'Thạc sĩ',
        specialization: 'Marketing',
        workload: 0,
        subjects: [],
        publications: [
          { title: 'Chiến lược marketing cho doanh nghiệp vừa và nhỏ', year: 2019, journal: 'Tạp chí Kinh tế và Quản trị Kinh doanh' },
          { title: 'Nghiên cứu hành vi người tiêu dùng trong thời đại số', year: 2018, journal: 'Tạp chí Marketing Việt Nam' }
        ]
      },
      {
        id: 'GV008',
        name: 'TS. Ngô Văn H',
        dob: '18/06/1978',
        gender: 'Nam',
        department: 'Điện - Điện tử',
        position: 'Trưởng khoa',
        joinDate: '10/10/2007',
        email: 'ngovanh@teacher.edu.vn',
        phone: '0934567812',
        address: '505 Đường Trần Hưng Đạo, Quận 1, TP.HCM',
        status: 'active',
        degree: 'Tiến sĩ',
        specialization: 'Tự động hóa',
        workload: 16,
        subjects: [
          { code: 'EE103', name: 'Điều khiển tự động', credits: 4, classes: ['DT1'], semester: 'HK1-2024' },
        ],
        publications: [
          { title: 'Hệ thống IoT trong quản lý nhà thông minh', year: 2023, journal: 'Tạp chí Khoa học Kỹ thuật' },
          { title: 'Phương pháp tối ưu hóa trong điều khiển tự động', year: 2021, journal: 'Tạp chí Điện - Điện tử Việt Nam' },
          { title: 'Ứng dụng trí tuệ nhân tạo trong hệ thống tự động hóa', year: 2020, journal: 'Tạp chí Khoa học và Công nghệ' }
        ]
      },
    ];
  
    // Filter teachers based on search term and filters
    const filteredTeachers = teachers.filter(teacher => {
      const matchesSearch = teacher.id.toLowerCase().includes(searchTerm.toLowerCase()) || 
                           teacher.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                           teacher.email.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesDepartment = selectedDepartment === 'all' || teacher.department === selectedDepartment;
      const matchesPosition = selectedPosition === 'all' || teacher.position === selectedPosition;
      const matchesSubject = selectedSubject === 'all' || 
                            teacher.subjects.some(subject => subject.name === selectedSubject);
      
      return matchesSearch && matchesDepartment && matchesPosition && matchesSubject;
    });
  
    // Handle teacher detail view
    const handleViewTeacher = (teacher) => {
      setCurrentTeacher(teacher);
      setShowDetailModal(true);
      setSelectedTab('info');
    };
  
    // Handle teacher deletion
    const handleDeleteTeacher = (teacherId) => {
      // In a real application, this would be an API call
      alert(`Xóa giáo viên ID: ${teacherId}`);
    };
  
    // Add new teacher
    const handleAddNewTeacher = () => {
      setShowAddModal(true);
    };
  
    // Get status color
    const getStatusColor = (status) => {
      switch (status) {
        case 'active': return 'bg-green-100 text-green-800';
        case 'on_leave': return 'bg-yellow-100 text-yellow-800';
        case 'retired': return 'bg-gray-100 text-gray-800';
        default: return 'bg-gray-100 text-gray-800';
      }
    };
  
    // Get status text
    const getStatusText = (status) => {
      switch (status) {
        case 'active': return 'Đang làm việc';
        case 'on_leave': return 'Nghỉ phép';
        case 'retired': return 'Đã nghỉ hưu';
        default: return status;
      }
    };

    // Calculate teaching stats
    const totalWorkload = teachers
      .filter(teacher => teacher.status === 'active')
      .reduce((acc, teacher) => acc + teacher.workload, 0);
    
    const averageWorkload = totalWorkload / teachers.filter(teacher => teacher.status === 'active').length || 0;
    
    const departmentStats = departments.map(dept => {
      const deptTeachers = teachers.filter(t => t.department === dept && t.status === 'active');
      return {
        name: dept,
        count: deptTeachers.length,
        avgWorkload: deptTeachers.reduce((acc, t) => acc + t.workload, 0) / deptTeachers.length || 0
      };
    });
    
    return (
      <div className="space-y-6">
        {/* Filters and search */}
        <div className="bg-white rounded-lg shadow p-4">
          <div className="mb-4 flex flex-col md:flex-row md:items-center md:justify-between">
            <h2 className="text-lg font-semibold text-gray-800 mb-2 md:mb-0">Danh sách giáo viên</h2>
            
            <div className="flex flex-col md:flex-row space-y-2 md:space-y-0 md:space-x-2">
              <div className="relative">
                <input 
                  type="text" 
                  placeholder="Tìm kiếm giáo viên..." 
                  className="border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 pl-8"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <Search size={16} className="absolute left-2 top-3 text-gray-400" />
              </div>
              
              <button 
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 flex items-center justify-center"
                onClick={handleAddNewTeacher}
              >
                <span>+ Thêm giáo viên mới</span>
              </button>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Khoa</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedDepartment}
                onChange={(e) => setSelectedDepartment(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {departments.map(department => (
                  <option key={department} value={department}>{department}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Chức vụ</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedPosition}
                onChange={(e) => setSelectedPosition(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {positions.map(position => (
                  <option key={position} value={position}>{position}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Môn giảng dạy</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedSubject}
                onChange={(e) => setSelectedSubject(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {subjects.map(subject => (
                  <option key={subject} value={subject}>{subject}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
              <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="all">Tất cả</option>
                <option value="active">Đang làm việc</option>
                <option value="on_leave">Nghỉ phép</option>
                <option value="retired">Đã nghỉ hưu</option>
              </select>
            </div>
          </div>
        </div>
        
        {/* Teacher list */}
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mã GV</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Họ tên</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Khoa</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Chức vụ</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Học hàm/Học vị</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Giờ dạy</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Trạng thái</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredTeachers.map(teacher => (
                  <tr key={teacher.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{teacher.id}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{teacher.name}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{teacher.department}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{teacher.position}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{teacher.degree}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{teacher.workload}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(teacher.status)}`}>
                        {getStatusText(teacher.status)}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <div className="flex space-x-2">
                        <button 
                          className="text-blue-600 hover:text-blue-900"
                          onClick={() => handleViewTeacher(teacher)}
                        >
                          Xem
                        </button>
                        <button className="text-green-600 hover:text-green-900">
                          Sửa
                        </button>
                        <button 
                          className="text-red-600 hover:text-red-900"
                          onClick={() => handleDeleteTeacher(teacher.id)}
                        >
                          Xóa
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          {filteredTeachers.length === 0 && (
            <div className="text-center py-4 text-gray-500">
              Không tìm thấy giáo viên nào phù hợp với bộ lọc
            </div>
          )}
        </div>
        
        {/* Teacher statistics */}
        <div className="bg-white rounded-lg shadow p-4">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">Thống kê giáo viên</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="p-4 bg-blue-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-blue-800">Tổng giáo viên</h3>
                <span className="text-2xl font-bold text-blue-600">
                  {teachers.length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-green-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-green-800">Đang làm việc</h3>
                <span className="text-2xl font-bold text-green-600">
                  {teachers.filter(teacher => teacher.status === 'active').length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-yellow-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-yellow-800">Nghỉ phép</h3>
                <span className="text-2xl font-bold text-yellow-600">
                  {teachers.filter(teacher => teacher.status === 'on_leave').length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-purple-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-purple-800">GD trung bình</h3>
                <span className="text-2xl font-bold text-purple-600">
                  {averageWorkload.toFixed(1)}
                </span>
              </div>
            </div>
          </div>
          
          <div className="mt-6">
            <h3 className="text-md font-medium text-gray-700 mb-2">Thống kê theo khoa</h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {departmentStats.map((dept, index) => (
                <div key={index} className="p-3 border rounded">
                  <div className="flex justify-between items-center mb-2">
                    <h4 className="font-medium">{dept.name}</h4>
                    <span className="text-sm font-medium">
                      {dept.count} giáo viên
                    </span>
                  </div>
                  <div className="flex justify-between text-sm">
                    <span className="text-gray-500">Giờ dạy trung bình:</span>
                    <span className="font-medium">{dept.avgWorkload.toFixed(1)}</span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
        
        {/* Teacher Detail Modal */}
        {showDetailModal && currentTeacher && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Thông tin giáo viên: {currentTeacher.name}</h3>
                <button onClick={() => setShowDetailModal(false)}>
                  <X size={20} />
                </button>
              </div>
              
              {/* Tabs */}
              <div className="border-b mb-4">
                <div className="flex space-x-4">
                  <button 
                    className={`pb-2 px-1 ${selectedTab === 'info' ? 'border-b-2 border-blue-500 text-blue-600' : 'text-gray-500'}`}
                    onClick={() => setSelectedTab('info')}
                  >
                    Thông tin cá nhân
                  </button>
                  <button 
                    className={`pb-2 px-1 ${selectedTab === 'teaching' ? 'border-b-2 border-blue-500 text-blue-600' : 'text-gray-500'}`}
                    onClick={() => setSelectedTab('teaching')}
                  >
                    Giảng dạy
                  </button>
                  <button 
                    className={`pb-2 px-1 ${selectedTab === 'publications' ? 'border-b-2 border-blue-500 text-blue-600' : 'text-gray-500'}`}
                    onClick={() => setSelectedTab('publications')}
                  >
                    Công bố khoa học
                  </button>
                </div>
              </div>
              
              {/* Tab Content */}
              <div>
                {/* Personal Info Tab */}
                {selectedTab === 'info' && (
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div className="col-span-2 flex items-start space-x-4 p-3 bg-gray-50 rounded-lg">
                      <div className="w-24 h-24 bg-gray-200 rounded-full flex items-center justify-center text-gray-400">
                        {currentTeacher.gender === 'Nam' ? 'Nam' : 'Nữ'}
                      </div>
                      <div>
                        <h4 className="text-xl font-semibold">{currentTeacher.name}</h4>
                        <p className="text-gray-600">{currentTeacher.position} - {currentTeacher.department}</p>
                        <div className="mt-2">
                          <span className={`px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(currentTeacher.status)}`}>
                            {getStatusText(currentTeacher.status)}
                          </span>
                        </div>
                      </div>
                    </div>
                    
                    <div className="space-y-3">
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Mã giáo viên</label>
                        <div className="mt-1 text-sm">{currentTeacher.id}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Ngày sinh</label>
                        <div className="mt-1 text-sm">{currentTeacher.dob}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Giới tính</label>
                        <div className="mt-1 text-sm">{currentTeacher.gender}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Ngày vào làm</label>
                        <div className="mt-1 text-sm">{currentTeacher.joinDate}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Học hàm/Học vị</label>
                        <div className="mt-1 text-sm">{currentTeacher.degree}</div>
                      </div>
                    </div>
                    
                    <div className="space-y-3">
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Email</label>
                        <div className="mt-1 text-sm">{currentTeacher.email}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Số điện thoại</label>
                        <div className="mt-1 text-sm">{currentTeacher.phone}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Địa chỉ</label>
                        <div className="mt-1 text-sm">{currentTeacher.address}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Chuyên ngành</label>
                        <div className="mt-1 text-sm">{currentTeacher.specialization}</div>
                      </div>
                      
                      <div>
                        <label className="block text-sm font-medium text-gray-500">Giờ giảng dạy</label>
                        <div className="mt-1 text-sm">{currentTeacher.workload} giờ/tuần</div>
                      </div>
                    </div>
                  </div>
                )}
                
                {/* Teaching Tab */}
                {selectedTab === 'teaching' && (
                  <div>
                    <h4 className="font-medium mb-3">Môn học giảng dạy (Học kỳ hiện tại)</h4>
                    
                    {currentTeacher.subjects.length > 0 ? (
                      <div className="bg-white divide-y divide-gray-200 border rounded">
                        {currentTeacher.subjects.map((subject, index) => (
                          <div key={index} className="p-3 hover:bg-gray-50">
                            <div className="flex justify-between mb-1">
                              <span className="font-medium">{subject.name}</span>
                              <span className="text-sm text-gray-500">{subject.code}</span>
                            </div>
                            <div className="text-sm text-gray-600">
                              {subject.credits} tín chỉ | Lớp: {subject.classes.join(', ')}
                            </div>
                            <div className="text-sm text-blue-600 mt-1">
                              {subject.semester}
                            </div>
                          </div>
                        ))}
                      </div>
                    ) : (
                      <div className="text-gray-500 italic">Không có môn học nào trong học kỳ hiện tại</div>
                    )}
                    
                    <div className="mt-4 flex justify-end">
                      <button className="bg-blue-500 text-white px-3 py-1 rounded text-sm hover:bg-blue-600">
                        Xem lịch sử giảng dạy
                      </button>
                    </div>
                  </div>
                )}
                
                {/* Publications Tab */}
                {selectedTab === 'publications' && (
                  <div>
                    <h4 className="font-medium mb-3">Công bố khoa học</h4>
                    
                    {currentTeacher.publications.length > 0 ? (
                      <div className="space-y-3">
                        {currentTeacher.publications.map((pub, index) => (
                          <div key={index} className="p-3 border rounded hover:bg-gray-50">
                            <div className="font-medium">{pub.title}</div>
                            <div className="flex justify-between mt-2 text-sm">
                              <span className="text-gray-600">{pub.journal}</span>
                              <span className="text-blue-600">Năm {pub.year}</span>
                            </div>
                          </div>
                        ))}
                      </div>
                    ) : (
                      <div className="text-gray-500 italic">Chưa có công bố khoa học nào</div>
                    )}
                    
                    <div className="mt-4 flex justify-end">
                      <button className="bg-green-500 text-white px-3 py-1 rounded text-sm hover:bg-green-600">
                        Thêm công bố mới
                      </button>
                    </div>
                  </div>
                )}
              </div>
              
              <div className="mt-6 flex justify-end space-x-3 pt-3 border-t">
                <button 
                  className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                  onClick={() => setShowDetailModal(false)}
                >
                  Đóng
                </button>
                <button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                  Chỉnh sửa thông tin
                </button>
              </div>
            </div>
          </div>
        )}
        
        {/* Add Teacher Modal */}
        {showAddModal && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Thêm giáo viên mới</h3>
                <button onClick={() => setShowAddModal(false)}>
                  <X size={20} />
                </button>
              </div>
              
              <form className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Mã giáo viên</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      placeholder="Nhập mã giáo viên"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Họ và tên</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      placeholder="Nhập họ và tên"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Ngày sinh</label>
                    <input 
                      type="date" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Giới tính</label>
                    <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                      <option value="">Chọn giới tính</option>
                      <option value="Nam">Nam</option>
                      <option value="Nữ">Nữ</option>
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Khoa</label>
                    <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                      <option value="">Chọn khoa</option>
                      {departments.map(department => (
                        <option key={department} value={department}>{department}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Chức vụ</label>
                    <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                      <option value="">Chọn chức vụ</option>
                      {positions.map(position => (
                        <option key={position} value={position}>{position}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Học hàm/Học vị</label>
                    <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                      <option value="">Chọn học hàm/học vị</option>
                      <option value="Giáo sư">Giáo sư</option>
                      <option value="Phó Giáo sư">Phó Giáo sư</option>
                      <option value="Tiến sĩ">Tiến sĩ</option>
                      <option value="Thạc sĩ">Thạc sĩ</option>
                      <option value="Cử nhân">Cử nhân</option>
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Chuyên ngành</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      placeholder="Nhập chuyên ngành"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                    <input 
                      type="email" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      placeholder="Nhập email"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Số điện thoại</label>
                    <input 
                      type="tel" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      placeholder="Nhập số điện thoại"
                    />
                  </div>
                  
                  <div className="col-span-2">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Địa chỉ</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      placeholder="Nhập địa chỉ"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Ngày vào làm</label>
                    <input 
                      type="date" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Giờ giảng dạy/tuần</label>
                    <input 
                      type="number" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      placeholder="Nhập số giờ giảng dạy"
                    />
                  </div>
                </div>
                
                <div className="mt-6 flex justify-end space-x-3 pt-3 border-t">
                  <button 
                    type="button"
                    className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                    onClick={() => setShowAddModal(false)}
                  >
                    Hủy
                  </button>
                  <button 
                    type="submit"
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                  >
                    Lưu giáo viên
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    );
}
function AnnouncementManagement() {
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('all');
    const [selectedPriority, setSelectedPriority] = useState('all');
    const [showAddModal, setShowAddModal] = useState(false);
    const [showDetailModal, setShowDetailModal] = useState(false);
    const [currentAnnouncement, setCurrentAnnouncement] = useState(null);
    const [showEditModal, setShowEditModal] = useState(false);
    
    // Mock data
    const categories = ['Thông báo chung', 'Kế hoạch học tập', 'Hoạt động sinh viên', 'Học bổng', 'Tuyển dụng'];
    const priorities = ['Cao', 'Trung bình', 'Thấp'];
    
    const announcements = [
      {
        id: 'TB001',
        title: 'Thông báo về kế hoạch học tập HK1 năm học 2024-2025',
        content: 'Phòng Đào tạo thông báo đến toàn thể sinh viên kế hoạch học tập học kỳ 1 năm học 2024-2025 như sau: [Chi tiết kế hoạch]...',
        publishDate: '15/07/2024',
        expireDate: '15/08/2024',
        author: 'Phòng Đào tạo',
        category: 'Kế hoạch học tập',
        priority: 'Cao',
        status: 'published',
        target: ['Tất cả sinh viên'],
        attachments: [
          { name: 'KeHoachHocTap_HK1_2024.pdf', size: '1.2MB' }
        ],
        views: 1250
      },
      {
        id: 'TB002',
        title: 'Thông báo về việc đăng ký học phần trực tuyến',
        content: 'Trung tâm Quản lý hệ thống thông tin thông báo lịch đăng ký học phần trực tuyến học kỳ 1 năm học 2024-2025 cho sinh viên các khóa như sau...',
        publishDate: '20/07/2024',
        expireDate: '05/08/2024',
        author: 'Trung tâm QLHTTT',
        category: 'Thông báo chung',
        priority: 'Cao',
        status: 'published',
        target: ['Sinh viên khóa 2021', 'Sinh viên khóa 2022', 'Sinh viên khóa 2023'],
        attachments: [
          { name: 'HuongDanDangKyHocPhan.pdf', size: '0.8MB' },
          { name: 'LichDangKyHocPhan.xlsx', size: '0.5MB' }
        ],
        views: 980
      },
      {
        id: 'TB003',
        title: 'Chương trình học bổng Toshiba 2024',
        content: 'Phòng Công tác sinh viên thông báo về chương trình học bổng Toshiba dành cho sinh viên có thành tích học tập xuất sắc năm học 2023-2024...',
        publishDate: '25/07/2024',
        expireDate: '25/08/2024',
        author: 'Phòng Công tác sinh viên',
        category: 'Học bổng',
        priority: 'Trung bình',
        status: 'published',
        target: ['Sinh viên khoa CNTT', 'Sinh viên khoa Điện - Điện tử'],
        attachments: [
          { name: 'ThongTinHocBongToshiba.pdf', size: '1.5MB' },
          { name: 'MauDonDangKy.docx', size: '0.3MB' }
        ],
        views: 450
      },
      {
        id: 'TB004',
        title: 'Cuộc thi "Sáng tạo công nghệ sinh viên" năm 2024',
        content: 'Đoàn Thanh niên - Hội Sinh viên tổ chức cuộc thi "Sáng tạo công nghệ sinh viên" năm 2024 với chủ đề "Công nghệ vì cộng đồng"...',
        publishDate: '30/07/2024',
        expireDate: '30/09/2024',
        author: 'Đoàn Thanh niên',
        category: 'Hoạt động sinh viên',
        priority: 'Trung bình',
        status: 'published',
        target: ['Tất cả sinh viên'],
        attachments: [
          { name: 'TheLeCuocThi.pdf', size: '1.0MB' }
        ],
        views: 320
      },
      {
        id: 'TB005',
        title: 'Thông báo tuyển dụng vị trí thực tập sinh tại công ty FPT Software',
        content: 'Trung tâm Hợp tác doanh nghiệp thông báo chương trình tuyển dụng thực tập sinh của công ty FPT Software cho sinh viên năm cuối...',
        publishDate: '05/08/2024',
        expireDate: '05/09/2024',
        author: 'Trung tâm HTDN',
        category: 'Tuyển dụng',
        priority: 'Thấp',
        status: 'published',
        target: ['Sinh viên năm cuối khoa CNTT', 'Sinh viên năm cuối khoa Điện - Điện tử'],
        attachments: [
          { name: 'ThongTinTuyenDung_FPT.pdf', size: '0.9MB' }
        ],
        views: 275
      },
      {
        id: 'TB006',
        title: 'Thông báo về việc đóng học phí học kỳ 1 năm học 2024-2025',
        content: 'Phòng Tài chính thông báo về việc đóng học phí học kỳ 1 năm học 2024-2025 cho sinh viên các khóa như sau...',
        publishDate: '10/08/2024',
        expireDate: '10/09/2024',
        author: 'Phòng Tài chính',
        category: 'Thông báo chung',
        priority: 'Cao',
        status: 'draft',
        target: ['Tất cả sinh viên'],
        attachments: [
          { name: 'MucHocPhi_HK1_2024.pdf', size: '0.7MB' },
          { name: 'HuongDanDongHocPhi.pdf', size: '0.6MB' }
        ],
        views: 0
      },
      {
        id: 'TB007',
        title: 'Thông báo lịch thi cuối kỳ HK2 năm học 2023-2024',
        content: 'Phòng Khảo thí thông báo lịch thi cuối kỳ học kỳ 2 năm học 2023-2024 như sau...',
        publishDate: '15/06/2024',
        expireDate: '15/07/2024',
        author: 'Phòng Khảo thí',
        category: 'Kế hoạch học tập',
        priority: 'Cao',
        status: 'expired',
        target: ['Tất cả sinh viên'],
        attachments: [
          { name: 'LichThiCuoiKy_HK2_2024.pdf', size: '1.3MB' }
        ],
        views: 1850
      },
      {
        id: 'TB008',
        title: 'Chương trình trao đổi sinh viên với Đại học Quốc gia Seoul',
        content: 'Phòng Hợp tác quốc tế thông báo chương trình trao đổi sinh viên với Đại học Quốc gia Seoul (Hàn Quốc) năm học 2024-2025...',
        publishDate: '12/08/2024',
        expireDate: '12/10/2024',
        author: 'Phòng HTQT',
        category: 'Học bổng',
        priority: 'Trung bình',
        status: 'published',
        target: ['Sinh viên năm 2', 'Sinh viên năm 3'],
        attachments: [
          { name: 'ThongTinChuongTrinh.pdf', size: '1.1MB' },
          { name: 'MauDonDangKy.docx', size: '0.4MB' }
        ],
        views: 185
      }
    ];
    
    // Filter announcements based on search term and filters
    const filteredAnnouncements = announcements.filter(announcement => {
      const matchesSearch = announcement.title.toLowerCase().includes(searchTerm.toLowerCase()) || 
                           announcement.author.toLowerCase().includes(searchTerm.toLowerCase()) ||
                           announcement.id.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesCategory = selectedCategory === 'all' || announcement.category === selectedCategory;
      const matchesPriority = selectedPriority === 'all' || announcement.priority === selectedPriority;
      
      return matchesSearch && matchesCategory && matchesPriority;
    });
    
    // Handle announcement detail view
    const handleViewAnnouncement = (announcement) => {
      setCurrentAnnouncement(announcement);
      setShowDetailModal(true);
    };
    
    // Handle announcement deletion
    const handleDeleteAnnouncement = (announcementId) => {
      // In a real application, this would be an API call
      alert(`Xóa thông báo ID: ${announcementId}`);
    };
    
    // Add new announcement
    const handleAddNewAnnouncement = () => {
      setShowAddModal(true);
    };
  
    // Edit announcement
    const handleEditAnnouncement = (announcement) => {
      setCurrentAnnouncement(announcement);
      setShowEditModal(true);
    };
    
    // Get status color
    const getStatusColor = (status) => {
      switch (status) {
        case 'published': return 'bg-green-100 text-green-800';
        case 'draft': return 'bg-yellow-100 text-yellow-800';
        case 'expired': return 'bg-gray-100 text-gray-800';
        default: return 'bg-gray-100 text-gray-800';
      }
    };
    
    // Get status text
    const getStatusText = (status) => {
      switch (status) {
        case 'published': return 'Đã đăng';
        case 'draft': return 'Bản nháp';
        case 'expired': return 'Hết hạn';
        default: return status;
      }
    };
  
    // Get priority color
    const getPriorityColor = (priority) => {
      switch (priority) {
        case 'Cao': return 'bg-red-100 text-red-800';
        case 'Trung bình': return 'bg-blue-100 text-blue-800';
        case 'Thấp': return 'bg-green-100 text-green-800';
        default: return 'bg-gray-100 text-gray-800';
      }
    };
  
    // Calculate stats
    const publishedCount = announcements.filter(a => a.status === 'published').length;
    const draftCount = announcements.filter(a => a.status === 'draft').length;
    const expiredCount = announcements.filter(a => a.status === 'expired').length;
    
    const categoryStats = categories.map(category => {
      const count = announcements.filter(a => a.category === category).length;
      return { name: category, count };
    });
      
    return (
      <div className="space-y-6">
        {/* Filters and search */}
        <div className="bg-white rounded-lg shadow p-4">
          <div className="mb-4 flex flex-col md:flex-row md:items-center md:justify-between">
            <h2 className="text-lg font-semibold text-gray-800 mb-2 md:mb-0">Danh sách thông báo</h2>
            
            <div className="flex flex-col md:flex-row space-y-2 md:space-y-0 md:space-x-2">
              <div className="relative">
                <input 
                  type="text" 
                  placeholder="Tìm kiếm thông báo..." 
                  className="border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 pl-8"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <svg xmlns="http://www.w3.org/2000/svg" className="absolute left-2 top-3 text-gray-400 h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
              
              <button 
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 flex items-center justify-center"
                onClick={handleAddNewAnnouncement}
              >
                <span>+ Thêm thông báo mới</span>
              </button>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Loại thông báo</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedCategory}
                onChange={(e) => setSelectedCategory(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {categories.map(category => (
                  <option key={category} value={category}>{category}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Mức độ ưu tiên</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedPriority}
                onChange={(e) => setSelectedPriority(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {priorities.map(priority => (
                  <option key={priority} value={priority}>{priority}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
              <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                <option value="all">Tất cả</option>
                <option value="published">Đã đăng</option>
                <option value="draft">Bản nháp</option>
                <option value="expired">Hết hạn</option>
              </select>
            </div>
          </div>
        </div>
        
        {/* Announcement list */}
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Tiêu đề</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Loại</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mức ưu tiên</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Người đăng</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Ngày đăng</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Trạng thái</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredAnnouncements.map(announcement => (
                  <tr key={announcement.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{announcement.id}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <div className="line-clamp-1">{announcement.title}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{announcement.category}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getPriorityColor(announcement.priority)}`}>
                        {announcement.priority}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{announcement.author}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{announcement.publishDate}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(announcement.status)}`}>
                        {getStatusText(announcement.status)}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <div className="flex space-x-2">
                        <button 
                          className="text-blue-600 hover:text-blue-900"
                          onClick={() => handleViewAnnouncement(announcement)}
                        >
                          Xem
                        </button>
                        <button 
                          className="text-green-600 hover:text-green-900"
                          onClick={() => handleEditAnnouncement(announcement)}
                        >
                          Sửa
                        </button>
                        <button 
                          className="text-red-600 hover:text-red-900"
                          onClick={() => handleDeleteAnnouncement(announcement.id)}
                        >
                          Xóa
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          {filteredAnnouncements.length === 0 && (
            <div className="text-center py-4 text-gray-500">
              Không tìm thấy thông báo nào phù hợp với bộ lọc
            </div>
          )}
        </div>
        
        {/* Announcement statistics */}
        <div className="bg-white rounded-lg shadow p-4">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">Thống kê thông báo</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="p-4 bg-blue-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-blue-800">Tổng thông báo</h3>
                <span className="text-2xl font-bold text-blue-600">
                  {announcements.length}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-green-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-green-800">Đã đăng</h3>
                <span className="text-2xl font-bold text-green-600">
                  {publishedCount}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-yellow-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-yellow-800">Bản nháp</h3>
                <span className="text-2xl font-bold text-yellow-600">
                  {draftCount}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-gray-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-gray-800">Hết hạn</h3>
                <span className="text-2xl font-bold text-gray-600">
                  {expiredCount}
                </span>
              </div>
            </div>
          </div>
          
          <div className="mt-6">
            <h3 className="text-md font-medium text-gray-700 mb-2">Thống kê theo loại thông báo</h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {categoryStats.map((cat, index) => (
                <div key={index} className="p-3 border rounded">
                  <div className="flex justify-between items-center">
                    <h4 className="font-medium">{cat.name}</h4>
                    <span className="text-sm font-medium">
                      {cat.count} thông báo
                    </span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
        
        {/* Announcement Detail Modal */}
        {showDetailModal && currentAnnouncement && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Chi tiết thông báo</h3>
                <button onClick={() => setShowDetailModal(false)}>
                  <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                  </svg>
                </button>
              </div>
              
              <div className="mb-4">
                <div className="flex justify-between items-start">
                  <h2 className="text-xl font-semibold">{currentAnnouncement.title}</h2>
                  <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(currentAnnouncement.status)}`}>
                    {getStatusText(currentAnnouncement.status)}
                  </span>
                </div>
                
                <div className="mt-2 text-sm text-gray-500">
                  <div className="flex space-x-4">
                    <span>ID: {currentAnnouncement.id}</span>
                    <span>• {currentAnnouncement.author}</span>
                    <span>• {currentAnnouncement.publishDate}</span>
                  </div>
                </div>
              </div>
              
              <div className="mb-4">
                <div className="flex space-x-3 mb-3">
                  <span className={`px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${getPriorityColor(currentAnnouncement.priority)}`}>
                    {currentAnnouncement.priority}
                  </span>
                  <span className="px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-purple-100 text-purple-800">
                    {currentAnnouncement.category}
                  </span>
                </div>
                
                <div className="p-4 bg-gray-50 rounded-lg">
                  <div className="whitespace-pre-line">
                    {currentAnnouncement.content}
                  </div>
                </div>
              </div>
              
              <div className="mb-4">
                <h4 className="font-medium mb-2">Đối tượng</h4>
                <div className="flex flex-wrap gap-2">
                  {currentAnnouncement.target.map((t, index) => (
                    <span key={index} className="px-2 py-1 bg-blue-100 text-blue-800 rounded text-xs">
                      {t}
                    </span>
                  ))}
                </div>
              </div>
              
              {currentAnnouncement.attachments.length > 0 && (
                <div className="mb-4">
                  <h4 className="font-medium mb-2">Tệp đính kèm</h4>
                  <div className="space-y-2">
                    {currentAnnouncement.attachments.map((attachment, index) => (
                      <div key={index} className="flex items-center p-2 border rounded">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-gray-500 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                        </svg>
                        <span className="flex-1">{attachment.name}</span>
                        <span className="text-sm text-gray-500">{attachment.size}</span>
                      </div>
                    ))}
                  </div>
                </div>
              )}
              
              <div className="grid grid-cols-2 gap-4 mb-4">
                <div>
                  <label className="block text-sm font-medium text-gray-500">Ngày đăng</label>
                  <div className="mt-1 text-sm">{currentAnnouncement.publishDate}</div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-500">Ngày hết hạn</label>
                  <div className="mt-1 text-sm">{currentAnnouncement.expireDate}</div>
                </div>
              </div>
              
              <div className="grid grid-cols-2 gap-4 mb-4">
                <div>
                  <label className="block text-sm font-medium text-gray-500">Lượt xem</label>
                  <div className="mt-1 text-sm">{currentAnnouncement.views}</div>
                </div>
              </div>
              
              <div className="mt-6 flex justify-end space-x-3 pt-3 border-t">
                <button 
                  className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                  onClick={() => setShowDetailModal(false)}
                >
                  Đóng
                </button>
                <button 
                  className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                  onClick={() => {
                    setShowDetailModal(false);
                    handleEditAnnouncement(currentAnnouncement);
                  }}
                >
                  Chỉnh sửa
                </button>
              </div>
            </div>
          </div>
        )}
        
        {/* Add Announcement Modal */}
        {showAddModal && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-semibold">Thêm thông báo mới</h3>
              <button onClick={() => setShowAddModal(false)}>
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
            
            <form className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Tiêu đề</label>
                <input 
                  type="text" 
                  className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="Nhập tiêu đề thông báo"
                />
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Loại thông báo</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">Chọn loại thông báo</option>
                    {categories.map(category => (
                      <option key={category} value={category}>{category}</option>
                    ))}
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Mức độ ưu tiên</label>
                  <select className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">Chọn mức độ ưu tiên</option>
                    {priorities.map(priority => (
                      <option key={priority} value={priority}>{priority}</option>
                    ))}
                  </select>
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nội dung</label>
                <textarea 
                  className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 min-h-[200px]" 
                  placeholder="Nhập nội dung thông báo"
                ></textarea>
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ngày đăng</label>
                  <input 
                    type="date" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  />
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ngày hết hạn</label>
                  <input 
                    type="date" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  />
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Người đăng</label>
                <input 
                  type="text" 
                  className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="Nhập tên người đăng"
                />
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Đối tượng</label>
                <div className="space-y-2">
                  <div className="flex items-center">
                    <input type="checkbox" id="target-all" className="mr-2" />
                    <label htmlFor="target-all">Tất cả sinh viên</label>
                  </div>
                  <div className="flex items-center">
                    <input type="checkbox" id="target-y1" className="mr-2" />
                    <label htmlFor="target-y1">Sinh viên năm 1</label>
                  </div>
                  <div className="flex items-center">
                    <input type="checkbox" id="target-y2" className="mr-2" />
                    <label htmlFor="target-y2">Sinh viên năm 2</label>
                  </div>
                  <div className="flex items-center">
                    <input type="checkbox" id="target-y3" className="mr-2" />
                    <label htmlFor="target-y3">Sinh viên năm 3</label>
                  </div>
                  <div className="flex items-center">
                    <input type="checkbox" id="target-y4" className="mr-2" />
                    <label htmlFor="target-y4">Sinh viên năm 4</label>
                  </div>
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Tệp đính kèm</label>
                <div className="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
                  <div className="space-y-1 text-center">
                    <svg xmlns="http://www.w3.org/2000/svg" className="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" />
                    </svg>
                    <div className="flex text-sm text-gray-600">
                      <label htmlFor="file-upload" className="relative cursor-pointer bg-white rounded-md font-medium text-blue-600 hover:text-blue-500 focus-within:outline-none">
                        <span>Tải lên tệp</span>
                        <input id="file-upload" name="file-upload" type="file" className="sr-only" multiple />
                      </label>
                      <p className="pl-1">hoặc kéo thả vào đây</p>
                    </div>
                    <p className="text-xs text-gray-500">
                      PNG, JPG, PDF, DOCX tối đa 10MB
                    </p>
                  </div>
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
                <div className="flex space-x-4">
                  <div className="flex items-center">
                    <input type="radio" id="status-publish" name="status" className="mr-2" />
                    <label htmlFor="status-publish">Đăng ngay</label>
                  </div>
                  <div className="flex items-center">
                    <input type="radio" id="status-draft" name="status" className="mr-2" />
                    <label htmlFor="status-draft">Lưu nháp</label>
                  </div>
                </div>
              </div>
              
              <div className="mt-6 flex justify-end space-x-3 pt-3 border-t">
                <button 
                  type="button"
                  className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                  onClick={() => setShowAddModal(false)}
                >
                  Hủy
                </button>
                <button 
                  type="submit"
                  className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                  Tạo thông báo
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
      
      {/* Edit Announcement Modal */}
      {showEditModal && currentAnnouncement && (
        <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
          <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-lg font-semibold">Chỉnh sửa thông báo</h3>
              <button onClick={() => setShowEditModal(false)}>
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
            
            <form className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Tiêu đề</label>
                <input 
                  type="text" 
                  className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  defaultValue={currentAnnouncement.title}
                />
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Loại thông báo</label>
                  <select 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    defaultValue={currentAnnouncement.category}
                  >
                    {categories.map(category => (
                      <option key={category} value={category}>{category}</option>
                    ))}
                  </select>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Mức độ ưu tiên</label>
                  <select 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    defaultValue={currentAnnouncement.priority}
                  >
                    {priorities.map(priority => (
                      <option key={priority} value={priority}>{priority}</option>
                    ))}
                  </select>
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nội dung</label>
                <textarea 
                  className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 min-h-[200px]" 
                  defaultValue={currentAnnouncement.content}
                ></textarea>
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ngày đăng</label>
                  <input 
                    type="text" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                    defaultValue={currentAnnouncement.publishDate}
                  />
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ngày hết hạn</label>
                  <input 
                    type="text" 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                    defaultValue={currentAnnouncement.expireDate}
                  />
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Người đăng</label>
                <input 
                  type="text" 
                  className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  defaultValue={currentAnnouncement.author}
                />
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Đối tượng</label>
                <div className="space-y-2">
                  {['Tất cả sinh viên', 'Sinh viên năm 1', 'Sinh viên năm 2', 'Sinh viên năm 3', 'Sinh viên năm 4'].map((target, idx) => (
                    <div key={idx} className="flex items-center">
                      <input 
                        type="checkbox" 
                        id={`target-edit-${idx}`} 
                        className="mr-2" 
                        defaultChecked={currentAnnouncement.target.includes(target)}
                      />
                      <label htmlFor={`target-edit-${idx}`}>{target}</label>
                    </div>
                  ))}
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Tệp đính kèm hiện tại</label>
                <div className="space-y-2">
                  {currentAnnouncement.attachments.map((attachment, index) => (
                    <div key={index} className="flex items-center p-2 border rounded justify-between">
                      <div className="flex items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-gray-500 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                        </svg>
                        <span>{attachment.name}</span>
                        <span className="ml-2 text-sm text-gray-500">({attachment.size})</span>
                      </div>
                      <button type="button" className="text-red-500 hover:text-red-700">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                        </svg>
                      </button>
                    </div>
                  ))}
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Thêm tệp đính kèm mới</label>
                <div className="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
                  <div className="space-y-1 text-center">
                    <svg xmlns="http://www.w3.org/2000/svg" className="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" />
                    </svg>
                    <div className="flex text-sm text-gray-600">
                      <label htmlFor="file-upload-edit" className="relative cursor-pointer bg-white rounded-md font-medium text-blue-600 hover:text-blue-500 focus-within:outline-none">
                        <span>Tải lên tệp</span>
                        <input id="file-upload-edit" name="file-upload-edit" type="file" className="sr-only" multiple />
                      </label>
                      <p className="pl-1">hoặc kéo thả vào đây</p>
                    </div>
                    <p className="text-xs text-gray-500">
                      PNG, JPG, PDF, DOCX tối đa 10MB
                    </p>
                  </div>
                </div>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
                <div className="flex space-x-4">
                  <div className="flex items-center">
                    <input 
                      type="radio" 
                      id="status-publish-edit" 
                      name="status-edit" 
                      className="mr-2"
                      defaultChecked={currentAnnouncement.status === 'published'} 
                    />
                    <label htmlFor="status-publish-edit">Đã đăng</label>
                  </div>
                  <div className="flex items-center">
                    <input 
                      type="radio" 
                      id="status-draft-edit" 
                      name="status-edit" 
                      className="mr-2"
                      defaultChecked={currentAnnouncement.status === 'draft'} 
                    />
                    <label htmlFor="status-draft-edit">Bản nháp</label>
                  </div>
                  <div className="flex items-center">
                    <input 
                      type="radio" 
                      id="status-expired-edit" 
                      name="status-edit" 
                      className="mr-2"
                      defaultChecked={currentAnnouncement.status === 'expired'} 
                    />
                    <label htmlFor="status-expired-edit">Hết hạn</label>
                  </div>
                </div>
              </div>
              
              <div className="mt-6 flex justify-end space-x-3 pt-3 border-t">
                <button 
                  type="button"
                  className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                  onClick={() => setShowEditModal(false)}
                >
                  Hủy
                </button>
                <button 
                  type="submit"
                  className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                  Lưu thay đổi
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
function AbsenceWarnings() {
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedDepartment, setSelectedDepartment] = useState('all');
    const [selectedStatus, setSelectedStatus] = useState('all');
    const [selectedSeverity, setSelectedSeverity] = useState('all');
    const [showAddModal, setShowAddModal] = useState(false);
    const [showDetailModal, setShowDetailModal] = useState(false);
    const [currentWarning, setCurrentWarning] = useState(null);
    const [showEditModal, setShowEditModal] = useState(false);
    const [selectedWarning, setSelectedWarning] = useState(null);
    
    // Mock data
    const departments = ['Công nghệ thông tin', 'Kinh tế', 'Ngoại ngữ', 'Điện - Điện tử'];
    const statuses = ['Chưa xử lý', 'Đang xử lý', 'Đã xử lý', 'Đã hủy'];
    const severityLevels = ['Nhẹ', 'Trung bình', 'Nghiêm trọng'];
    const warningTypes = ['Vắng học quá số buổi', 'Điểm quá trình thấp', 'Học lực yếu', 'Nợ học phí', 'Vi phạm quy chế'];
    
    // Mock warnings data
    const [warnings, setWarnings] = useState([
      {
        id: 'ACDW001',
        studentId: 'SV001',
        studentName: 'Nguyễn Văn A',
        department: 'Công nghệ thông tin',
        subject: 'Lập trình Web',
        class: 'CNTT1',
        warningDate: '10/04/2025',
        warningType: 'Vắng học quá số buổi',
        absences: 5,
        reason: 'Vắng quá 30% số buổi học',
        status: 'Đã xử lý',
        severity: 'Nhẹ',
        submittedBy: 'Trần Văn X',
        submittedDate: '11/04/2025',
        processedBy: 'Phạm Thị Y',
        processedDate: '12/04/2025',
        notes: 'Sinh viên đã nộp đơn xin phép và cam kết không tái phạm.'
      },
      {
        id: 'ACDW002',
        studentId: 'SV002',
        studentName: 'Trần Thị B',
        department: 'Kinh tế',
        subject: 'Kinh tế vĩ mô',
        class: 'KT1',
        warningDate: '12/04/2025',
        warningType: 'Điểm quá trình thấp',
        absences: 0,
        reason: 'Điểm quá trình dưới 3.0',
        status: 'Đang xử lý',
        severity: 'Trung bình',
        submittedBy: 'Lê Thị Z',
        submittedDate: '12/04/2025',
        processedBy: '',
        processedDate: '',
        notes: 'Đã liên hệ với sinh viên và đề nghị đăng ký học lại.'
      },
      {
        id: 'ACDW003',
        studentId: 'SV003',
        studentName: 'Lê Minh C',
        department: 'Công nghệ thông tin',
        subject: 'Cơ sở dữ liệu',
        class: 'CNTT2',
        warningDate: '08/04/2025',
        warningType: 'Học lực yếu',
        absences: 3,
        reason: 'GPA < 1.5 trong 2 kỳ liên tiếp',
        status: 'Chưa xử lý',
        severity: 'Nghiêm trọng',
        submittedBy: 'Nguyễn Thị M',
        submittedDate: '08/04/2025',
        processedBy: '',
        processedDate: '',
        notes: 'Sinh viên cần gặp cố vấn học tập để được tư vấn.'
      },
      {
        id: 'ACDW004',
        studentId: 'SV008',
        studentName: 'Ngô Văn H',
        department: 'Điện - Điện tử',
        subject: 'Điều khiển tự động',
        class: 'DT1',
        warningDate: '05/04/2025',
        warningType: 'Nợ học phí',
        absences: 0,
        reason: 'Chưa đóng học phí học kỳ',
        status: 'Đã xử lý',
        severity: 'Nhẹ',
        submittedBy: 'Lê Văn K',
        submittedDate: '05/04/2025',
        processedBy: 'Phạm Thị Y',
        processedDate: '07/04/2025',
        notes: 'Sinh viên đã nộp học phí đầy đủ.'
      },
      {
        id: 'ACDW005',
        studentId: 'SV005',
        studentName: 'Vũ Đức E',
        department: 'Điện - Điện tử',
        subject: 'Mạch điện tử',
        class: 'DT1',
        warningDate: '15/04/2025',
        warningType: 'Vi phạm quy chế',
        absences: 0,
        reason: 'Gian lận trong kỳ thi giữa kỳ',
        status: 'Chưa xử lý',
        severity: 'Nghiêm trọng',
        submittedBy: 'Trần Văn X',
        submittedDate: '15/04/2025',
        processedBy: '',
        processedDate: '',
        notes: 'Sinh viên cần gặp hội đồng kỷ luật vào ngày 20/04/2025.'
      },
    ]);
    
    // Filter warnings based on search term and filters
    const filteredWarnings = warnings.filter(warning => {
      const matchesSearch = 
        warning.id.toLowerCase().includes(searchTerm.toLowerCase()) || 
        warning.studentId.toLowerCase().includes(searchTerm.toLowerCase()) || 
        warning.studentName.toLowerCase().includes(searchTerm.toLowerCase());
      
      const matchesDepartment = selectedDepartment === 'all' || warning.department === selectedDepartment;
      const matchesStatus = selectedStatus === 'all' || warning.status === selectedStatus;
      const matchesSeverity = selectedSeverity === 'all' || warning.severity === selectedSeverity;
      
      return matchesSearch && matchesDepartment && matchesStatus && matchesSeverity;
    });
    
    // Handle warning detail view
    const handleViewWarning = (warning) => {
      setCurrentWarning(warning);
      setShowDetailModal(true);
    };
    
    // Handle warning deletion
    const handleDeleteWarning = (warningId) => {
      if (confirm("Bạn có chắc chắn muốn xóa cảnh báo này không?")) {
        setWarnings(warnings.filter(warning => warning.id !== warningId));
      }
    };
    
    // Handle add new warning
    const handleAddNewWarning = () => {
      setShowAddModal(true);
    };
    
    // Handle edit warning
    const handleEditWarning = (warning) => {
      setSelectedWarning(warning);
      setShowEditModal(true);
    };
    
    // Handle save new warning
    const handleSaveNewWarning = (e) => {
      e.preventDefault();
      
      // In a real application, this would add the new warning to database
      // Here, we'll just show an alert to simulate
      alert('Đã thêm cảnh báo học vụ mới thành công!');
      setShowAddModal(false);
    };
    
    // Handle save edited warning
    const handleSaveEditedWarning = (e) => {
      e.preventDefault();
      
      // In a real application, this would update the warning in database
      // Here, we'll just show an alert to simulate
      alert('Đã cập nhật cảnh báo học vụ thành công!');
      setShowEditModal(false);
    };
    
    // Get status color
    const getStatusColor = (status) => {
      switch (status) {
        case 'Chưa xử lý': return 'bg-red-100 text-red-800';
        case 'Đang xử lý': return 'bg-yellow-100 text-yellow-800';
        case 'Đã xử lý': return 'bg-green-100 text-green-800';
        case 'Đã hủy': return 'bg-gray-100 text-gray-800';
        default: return 'bg-gray-100 text-gray-800';
      }
    };
    
    // Get severity color
    const getSeverityColor = (severity) => {
      switch (severity) {
        case 'Nhẹ': return 'bg-blue-100 text-blue-800';
        case 'Trung bình': return 'bg-yellow-100 text-yellow-800';
        case 'Nghiêm trọng': return 'bg-red-100 text-red-800';
        default: return 'bg-gray-100 text-gray-800';
      }
    };
    
    // Calculate statistics
    const totalWarnings = warnings.length;
    const pendingWarnings = warnings.filter(w => w.status === 'Chưa xử lý').length;
    const processingWarnings = warnings.filter(w => w.status === 'Đang xử lý').length;
    const resolvedWarnings = warnings.filter(w => w.status === 'Đã xử lý').length;
    
    const departmentStats = departments.map(dept => {
      const deptWarnings = warnings.filter(w => w.department === dept);
      return {
        name: dept,
        count: deptWarnings.length,
        pending: deptWarnings.filter(w => w.status === 'Chưa xử lý').length
      };
    });
    
    return (
      <div className="space-y-6">
        {/* Filters and search */}
        <div className="bg-white rounded-lg shadow p-4">
          <div className="mb-4 flex flex-col md:flex-row md:items-center md:justify-between">
            <h2 className="text-lg font-semibold text-gray-800 mb-2 md:mb-0">Danh sách cảnh báo học vụ sinh viên</h2>
            
            <div className="flex flex-col md:flex-row space-y-2 md:space-y-0 md:space-x-2">
              <div className="relative">
                <input 
                  type="text" 
                  placeholder="Tìm kiếm cảnh báo..." 
                  className="border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 pl-8"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <svg xmlns="http://www.w3.org/2000/svg" className="absolute left-2 top-3 h-4 w-4 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
              
              <button 
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 flex items-center justify-center"
                onClick={handleAddNewWarning}
              >
                <span>+ Thêm cảnh báo mới</span>
              </button>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Khoa</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedDepartment}
                onChange={(e) => setSelectedDepartment(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {departments.map(department => (
                  <option key={department} value={department}>{department}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedStatus}
                onChange={(e) => setSelectedStatus(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {statuses.map(status => (
                  <option key={status} value={status}>{status}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Mức độ</label>
              <select 
                className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                value={selectedSeverity}
                onChange={(e) => setSelectedSeverity(e.target.value)}
              >
                <option value="all">Tất cả</option>
                {severityLevels.map(level => (
                  <option key={level} value={level}>{level}</option>
                ))}
              </select>
            </div>
          </div>
        </div>
        
        {/* Warnings list */}
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mã</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">MSSV</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Họ tên SV</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Lớp</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Loại cảnh báo</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Trạng thái</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mức độ</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Thao tác</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredWarnings.map(warning => (
                  <tr key={warning.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{warning.id}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{warning.studentId}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{warning.studentName}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{warning.class}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{warning.warningType}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(warning.status)}`}>
                        {warning.status}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${getSeverityColor(warning.severity)}`}>
                        {warning.severity}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <div className="flex space-x-2">
                        <button 
                          className="text-blue-600 hover:text-blue-900"
                          onClick={() => handleViewWarning(warning)}
                        >
                          Xem
                        </button>
                        <button 
                          className="text-green-600 hover:text-green-900"
                          onClick={() => handleEditWarning(warning)}
                        >
                          Sửa
                        </button>
                        <button 
                          className="text-red-600 hover:text-red-900"
                          onClick={() => handleDeleteWarning(warning.id)}
                        >
                          Xóa
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          {filteredWarnings.length === 0 && (
            <div className="text-center py-4 text-gray-500">
              Không tìm thấy cảnh báo học vụ nào phù hợp với bộ lọc
            </div>
          )}
        </div>
        
        {/* Warnings statistics */}
        <div className="bg-white rounded-lg shadow p-4">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">Thống kê cảnh báo học vụ</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="p-4 bg-blue-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-blue-800">Tổng cảnh báo</h3>
                <span className="text-2xl font-bold text-blue-600">
                  {totalWarnings}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-red-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-red-800">Chưa xử lý</h3>
                <span className="text-2xl font-bold text-red-600">
                  {pendingWarnings}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-yellow-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-yellow-800">Đang xử lý</h3>
                <span className="text-2xl font-bold text-yellow-600">
                  {processingWarnings}
                </span>
              </div>
            </div>
            
            <div className="p-4 bg-green-50 rounded-lg">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-medium text-green-800">Đã xử lý</h3>
                <span className="text-2xl font-bold text-green-600">
                  {resolvedWarnings}
                </span>
              </div>
            </div>
          </div>
          
          <div className="mt-6">
            <h3 className="text-md font-medium text-gray-700 mb-2">Thống kê theo khoa</h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {departmentStats.map((dept, index) => (
                <div key={index} className="p-3 border rounded">
                  <div className="flex justify-between items-center mb-2">
                    <h4 className="font-medium">{dept.name}</h4>
                    <span className="text-sm font-medium">
                      {dept.count} cảnh báo
                    </span>
                  </div>
                  <div className="flex justify-between text-sm">
                    <span className="text-gray-500">Đang chờ xử lý:</span>
                    <span className="font-medium text-red-600">{dept.pending}</span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
        
        {/* Warning Detail Modal */}
        {showDetailModal && currentWarning && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-2xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Chi tiết cảnh báo học vụ: {currentWarning.id}</h3>
                <button onClick={() => setShowDetailModal(false)}>
                  <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                  </svg>
                </button>
              </div>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                <div className="space-y-3">
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Mã cảnh báo</label>
                    <div className="mt-1 text-sm">{currentWarning.id}</div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">MSSV</label>
                    <div className="mt-1 text-sm">{currentWarning.studentId}</div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Họ tên sinh viên</label>
                    <div className="mt-1 text-sm">{currentWarning.studentName}</div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Khoa</label>
                    <div className="mt-1 text-sm">{currentWarning.department}</div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Môn học</label>
                    <div className="mt-1 text-sm">{currentWarning.subject}</div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Lớp</label>
                    <div className="mt-1 text-sm">{currentWarning.class}</div>
                  </div>
                </div>
                
                <div className="space-y-3">
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Ngày cảnh báo</label>
                    <div className="mt-1 text-sm">{currentWarning.warningDate}</div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Loại cảnh báo</label>
                    <div className="mt-1 text-sm">{currentWarning.warningType}</div>
                  </div>
                  
                  {currentWarning.absences > 0 && (
                    <div>
                      <label className="block text-sm font-medium text-gray-500">Số buổi vắng</label>
                      <div className="mt-1 text-sm">{currentWarning.absences} buổi</div>
                    </div>
                  )}
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Lý do cảnh báo</label>
                    <div className="mt-1 text-sm">{currentWarning.reason}</div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Trạng thái</label>
                    <div className="mt-1">
                      <span className={`px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${getStatusColor(currentWarning.status)}`}>
                        {currentWarning.status}
                      </span>
                    </div>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-500">Mức độ</label>
                    <div className="mt-1">
                      <span className={`px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${getSeverityColor(currentWarning.severity)}`}>
                        {currentWarning.severity}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
              
              <div className="space-y-3 mt-4 border-t pt-4">
                <div>
                  <label className="block text-sm font-medium text-gray-500">Người báo cáo</label>
                  <div className="mt-1 text-sm">{currentWarning.submittedBy}</div>
                </div>
                
                <div>
                  <label className="block text-sm font-medium text-gray-500">Ngày báo cáo</label>
                  <div className="mt-1 text-sm">{currentWarning.submittedDate}</div>
                </div>
                
                {currentWarning.processedBy && (
                  <>
                    <div>
                      <label className="block text-sm font-medium text-gray-500">Người xử lý</label>
                      <div className="mt-1 text-sm">{currentWarning.processedBy}</div>
                    </div>
                    
                    <div>
                      <label className="block text-sm font-medium text-gray-500">Ngày xử lý</label>
                      <div className="mt-1 text-sm">{currentWarning.processedDate}</div>
                    </div>
                  </>
                )}
                
                <div>
                  <label className="block text-sm font-medium text-gray-500">Ghi chú</label>
                  <div className="mt-1 text-sm p-2 bg-gray-50 rounded">{currentWarning.notes}</div>
                </div>
              </div>
              
              <div className="mt-6 flex justify-end space-x-3 pt-3 border-t">
                <button 
                  className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                  onClick={() => setShowDetailModal(false)}
                >
                  Đóng
                </button>
                {currentWarning.status !== 'Đã xử lý' && currentWarning.status !== 'Đã hủy' && (
                  <button className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                    Xử lý cảnh báo
                  </button>
                )}
              </div>
            </div>
          </div>
        )}
        
        {/* Add Warning Modal */}
        {showAddModal && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-2xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Thêm cảnh báo học vụ mới</h3>
                <button onClick={() => setShowAddModal(false)}>
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                  </svg>
                </button>
              </div>
              
              <form onSubmit={handleSaveNewWarning}>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">MSSV</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Họ tên sinh viên</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Khoa</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required
                    >
                      <option value="">-- Chọn khoa --</option>
                      {departments.map(department => (
                        <option key={department} value={department}>{department}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Lớp</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Môn học</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Loại cảnh báo</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required
                    >
                      <option value="">-- Chọn loại cảnh báo --</option>
                      {warningTypes.map(type => (
                        <option key={type} value={type}>{type}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Số buổi vắng</label>
                    <input 
                      type="number" 
                      min="0"
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Mức độ</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      required
                    >
                      <option value="">-- Chọn mức độ --</option>
                      {severityLevels.map(level => (
                        <option key={level} value={level}>{level}</option>
                      ))}
                    </select>
                  </div>
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Lý do cảnh báo</label>
                  <textarea 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    rows="3"
                    required
                  ></textarea>
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ghi chú</label>
                  <textarea 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    rows="3"
                  ></textarea>
                </div>
                
                <div className="flex justify-end space-x-3 pt-3 border-t">
                  <button 
                    type="button"
                    className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                    onClick={() => setShowAddModal(false)}
                  >
                    Hủy
                  </button>
                  <button 
                    type="submit"
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                  >
                    Lưu cảnh báo
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
        
        {/* Edit Warning Modal */}
        {showEditModal && selectedWarning && (
          <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-2xl p-6 rounded-lg shadow-lg max-h-screen overflow-y-auto">
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-lg font-semibold">Chỉnh sửa cảnh báo: {selectedWarning.id}</h3>
                <button onClick={() => setShowEditModal(false)}>
                  <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                  </svg>
                </button>
              </div>
              
              <form onSubmit={handleSaveEditedWarning}>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">MSSV</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.studentId}
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Họ tên sinh viên</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.studentName}
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Khoa</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.department}
                      required
                    >
                      {departments.map(department => (
                        <option key={department} value={department}>{department}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Lớp</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.class}
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Môn học</label>
                    <input 
                      type="text" 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.subject}
                      required
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Loại cảnh báo</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.warningType}
                      required
                    >
                      {warningTypes.map(type => (
                        <option key={type} value={type}>{type}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Số buổi vắng</label>
                    <input 
                      type="number" 
                      min="0"
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.absences}
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Mức độ</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.severity}
                      required
                    >
                      {severityLevels.map(level => (
                        <option key={level} value={level}>{level}</option>
                      ))}
                    </select>
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Trạng thái</label>
                    <select 
                      className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      defaultValue={selectedWarning.status}
                      required
                    >
                      {statuses.map(status => (
                        <option key={status} value={status}>{status}</option>
                      ))}
                    </select>
                  </div>
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Lý do cảnh báo</label>
                  <textarea 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    rows="3"
                    defaultValue={selectedWarning.reason}
                    required
                  ></textarea>
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ghi chú</label>
                  <textarea 
                    className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    rows="3"
                    defaultValue={selectedWarning.notes}
                  ></textarea>
                </div>
                
                {selectedWarning.status === 'Đã xử lý' && (
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Người xử lý</label>
                      <input 
                        type="text" 
                        className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        defaultValue={selectedWarning.processedBy}
                      />
                    </div>
                    
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-1">Ngày xử lý</label>
                      <input 
                        type="text" 
                        className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        defaultValue={selectedWarning.processedDate}
                      />
                    </div>
                  </div>
                )}
                
                <div className="flex justify-end space-x-3 pt-3 border-t">
                  <button 
                    type="button"
                    className="bg-gray-200 px-4 py-2 rounded hover:bg-gray-300 text-gray-800"
                    onClick={() => setShowEditModal(false)}
                  >
                    Hủy
                  </button>
                  <button 
                    type="submit"
                    className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
                  >
                    Cập nhật
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
        
        {/* Process Warning Modal can be added here if needed */}
        
      </div>
    );
}
