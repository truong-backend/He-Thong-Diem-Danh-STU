import { useState } from 'react';
import { 
  Calendar, 
  BarChart, 
  ChevronDown, 
  QrCode, 
  UserCheck, 
  ClipboardList, 
  FileText, 
  Bell, 
  PieChart, 
  Download, 
  UserX, 
  Settings,
  Menu,
  X,
  Search,
  LogOut
} from 'lucide-react';

// Main App Component
export default function LecturerDashboard() {
  const [currentView, setCurrentView] = useState('dashboard');
  const [isSidebarOpen, setSidebarOpen] = useState(true);
  const [isMobileMenuOpen, setMobileMenuOpen] = useState(false);
  
  // Mock data
  const mockLecturerInfo = {
    name: "TS. Nguyễn Văn A",
    department: "Khoa Công nghệ Thông tin",
    avatar: "/api/placeholder/50/50"
  };

  const toggleMobileMenu = () => {
    setMobileMenuOpen(!isMobileMenuOpen);
  };
  
  // Render current view based on selection
  const renderView = () => {
    switch(currentView) {
      case 'dashboard':
        return <DashboardView />;
      case 'schedule':
        return <ScheduleView />;
      case 'generateCode':
        return <GenerateAttendanceCodeView />;
      case 'manualAttendance':
        return <ManualAttendanceView />;
      case 'attendanceList':
        return <AttendanceListView />;
      case 'absenceRequests':
        return <AbsenceRequestsView />;
      case 'notifications':
        return <NotificationsView />;
      case 'statistics':
        return <StatisticsView />;
      case 'reports':
        return <ReportsView />;
      case 'editAttendance':
        return <EditAttendanceView />;
      default:
        return <DashboardView />;
    }
  };

  // Navigation items
  const navItems = [
    { id: 'dashboard', label: 'Tổng quan', icon: <BarChart size={20} /> },
    { id: 'schedule', label: 'Lịch giảng dạy', icon: <Calendar size={20} /> },
    { id: 'generateCode', label: 'Tạo mã điểm danh', icon: <QrCode size={20} /> },
    { id: 'manualAttendance', label: 'Điểm danh thủ công', icon: <UserCheck size={20} /> },
    { id: 'attendanceList', label: 'Danh sách điểm danh', icon: <ClipboardList size={20} /> },
    { id: 'absenceRequests', label: 'Đơn xin phép vắng', icon: <FileText size={20} /> },
    { id: 'notifications', label: 'Gửi thông báo', icon: <Bell size={20} /> },
    { id: 'statistics', label: 'Thống kê điểm danh', icon: <PieChart size={20} /> },
    { id: 'reports', label: 'Xuất báo cáo', icon: <Download size={20} /> },
    { id: 'editAttendance', label: 'Điều chỉnh trạng thái', icon: <UserX size={20} /> },
    { id: '', label: 'Đăng xuất', icon: <UserX size={20} /> },
    
  ];

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar for desktop */}
      <div className={`${isSidebarOpen ? 'w-64' : 'w-20'} hidden md:block bg-blue-800 text-white transition-all duration-300 ease-in-out`}>
        <div className="p-4 flex items-center justify-between">
          <h1 className={`${isSidebarOpen ? 'block' : 'hidden'} text-lg font-bold`}>STU Điểm danh</h1>
          <button 
            onClick={() => setSidebarOpen(!isSidebarOpen)}
            className="p-1 rounded-full hover:bg-blue-700"
          >
            <ChevronDown className={`transform ${isSidebarOpen ? '' : 'rotate-90'} transition-transform duration-300`} size={20} />
          </button>
        </div>
        
        {/* Lecturer info */}
        <div className="p-4 border-b border-blue-700">
          <div className="flex items-center">
            <img src={mockLecturerInfo.avatar} alt="Avatar" className="w-10 h-10 rounded-full" />
            <div className={`${isSidebarOpen ? 'block ml-3' : 'hidden'}`}>
              <p className="text-sm font-medium">{mockLecturerInfo.name}</p>
              <p className="text-xs text-blue-200">{mockLecturerInfo.department}</p>
            </div>
          </div>
        </div>
        
        {/* Navigation */}
        <nav className="mt-5">
          <ul>
            {navItems.map(item => (
              <li key={item.id}>
                <button
                  onClick={() => setCurrentView(item.id)}
                  className={`flex items-center w-full p-3 ${currentView === item.id ? 'bg-blue-900' : 'hover:bg-blue-700'} transition-colors duration-200`}
                >
                  <span className="flex items-center justify-center w-6">{item.icon}</span>
                  {isSidebarOpen && <span className="ml-3">{item.label}</span>}
                </button>
              </li>
            ))}
          </ul>
        </nav>


      </div>

      {/* Mobile Menu Button */}
      <div className="md:hidden fixed top-0 left-0 z-20 bg-blue-800 w-full flex items-center justify-between p-4">
        <h1 className="text-white font-bold">STU Điểm danh</h1>
        <button 
          onClick={toggleMobileMenu}
          className="text-white"
        >
          {isMobileMenuOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
      </div>

      {/* Mobile sidebar */}
      {isMobileMenuOpen && (
        <div className="md:hidden fixed top-16 left-0 z-10 bg-blue-800 text-white w-full h-screen">
          <div className="p-4 border-b border-blue-700">
            <div className="flex items-center">
              <img src={mockLecturerInfo.avatar} alt="Avatar" className="w-10 h-10 rounded-full" />
              <div className="ml-3">
                <p className="text-sm font-medium">{mockLecturerInfo.name}</p>
                <p className="text-xs text-blue-200">{mockLecturerInfo.department}</p>
              </div>
            </div>
          </div>
          
          <nav className="mt-5">
            <ul>
              {navItems.map(item => (
                <li key={item.id}>
                  <button
                    onClick={() => {
                      setCurrentView(item.id);
                      setMobileMenuOpen(false);
                    }}
                    className={`flex items-center w-full p-4 ${currentView === item.id ? 'bg-blue-900' : 'hover:bg-blue-700'} transition-colors duration-200`}
                  >
                    <span className="flex items-center justify-center w-6">{item.icon}</span>
                    <span className="ml-3">{item.label}</span>
                  </button>
                </li>
              ))}
            </ul>
          </nav>

          {/* Logout button */}
          <div className="absolute bottom-0 w-full p-4 border-t border-blue-700">
            <button className="flex items-center w-full p-2 rounded hover:bg-blue-700 transition-colors duration-200">
              <LogOut size={20} />
              <span className="ml-3">Đăng xuất</span>
            </button>
          </div>
        </div>
      )}

      {/* Main content */}
      <div className="flex-1 overflow-x-hidden overflow-y-auto pt-16 md:pt-0">
        <div className="container mx-auto px-4 py-6">
          {renderView()}
        </div>
      </div>
    </div>
  );
}
// Dashboard View
function DashboardView() {
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Tổng quan</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-gray-500">Lớp hôm nay</p>
              <h3 className="text-2xl font-bold">3 lớp</h3>
            </div>
            <Calendar className="text-blue-500" size={24} />
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-gray-500">Đơn xin phép chờ duyệt</p>
              <h3 className="text-2xl font-bold">5 đơn</h3>
            </div>
            <FileText className="text-orange-500" size={24} />
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-gray-500">Tỷ lệ đi học trung bình</p>
              <h3 className="text-2xl font-bold">87%</h3>
            </div>
            <BarChart className="text-green-500" size={24} />
          </div>
        </div>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Lịch dạy hôm nay</h3>
          <div className="space-y-4">
            <div className="p-3 border-l-4 border-blue-500 bg-blue-50 rounded">
              <p className="font-semibold">Lập trình web nâng cao (IT505)</p>
              <p className="text-sm text-gray-600">08:00 - 11:30 | Phòng A1.01</p>
            </div>
            <div className="p-3 border-l-4 border-green-500 bg-green-50 rounded">
              <p className="font-semibold">Cơ sở dữ liệu (IT202)</p>
              <p className="text-sm text-gray-600">13:00 - 16:30 | Phòng B2.05</p>
            </div>
            <div className="p-3 border-l-4 border-purple-500 bg-purple-50 rounded">
              <p className="font-semibold">Phân tích thiết kế hệ thống (IT303)</p>
              <p className="text-sm text-gray-600">18:00 - 21:30 | Phòng C3.08</p>
            </div>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Đơn xin phép gần đây</h3>
          <div className="space-y-4">
            <div className="flex items-center justify-between p-3 border border-gray-200 rounded">
              <div>
                <p className="font-semibold">Nguyễn Văn B - 20110123</p>
                <p className="text-sm text-gray-600">IT505 - 03/05/2025</p>
              </div>
              <div className="flex space-x-2">
                <button className="px-3 py-1 bg-green-100 text-green-600 rounded-md text-sm">Duyệt</button>
                <button className="px-3 py-1 bg-red-100 text-red-600 rounded-md text-sm">Từ chối</button>
              </div>
            </div>
            <div className="flex items-center justify-between p-3 border border-gray-200 rounded">
              <div>
                <p className="font-semibold">Trần Thị C - 20110456</p>
                <p className="text-sm text-gray-600">IT202 - 04/05/2025</p>
              </div>
              <div className="flex space-x-2">
                <button className="px-3 py-1 bg-green-100 text-green-600 rounded-md text-sm">Duyệt</button>
                <button className="px-3 py-1 bg-red-100 text-red-600 rounded-md text-sm">Từ chối</button>
              </div>
            </div>
            <div className="flex items-center justify-between p-3 border border-gray-200 rounded">
              <div>
                <p className="font-semibold">Lê Văn D - 20110789</p>
                <p className="text-sm text-gray-600">IT303 - 05/05/2025</p>
              </div>
              <div className="flex space-x-2">
                <button className="px-3 py-1 bg-green-100 text-green-600 rounded-md text-sm">Duyệt</button>
                <button className="px-3 py-1 bg-red-100 text-red-600 rounded-md text-sm">Từ chối</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
// Schedule View
function ScheduleView() {
  const weekdays = ['Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7', 'Chủ nhật'];
  const timeSlots = ['07:00 - 09:30', '09:45 - 12:15', '12:30 - 15:00', '15:15 - 17:45', '18:00 - 20:30'];
  
  // Mock schedule data
  const scheduleData = [
    { day: 'Thứ 2', time: '07:00 - 09:30', subject: 'Lập trình web nâng cao', room: 'A1.01', class: 'IT505' },
    { day: 'Thứ 3', time: '09:45 - 12:15', subject: 'Cơ sở dữ liệu', room: 'B2.05', class: 'IT202' },
    { day: 'Thứ 5', time: '15:15 - 17:45', subject: 'Phân tích thiết kế hệ thống', room: 'C3.08', class: 'IT303' },
    { day: 'Thứ 6', time: '18:00 - 20:30', subject: 'Lập trình web nâng cao', room: 'A1.01', class: 'IT505' },
  ];
  
  // Function to get class for a specific day and time
  const getClass = (day, time) => {
    const classInfo = scheduleData.find(item => item.day === day && item.time === time);
    return classInfo ? (
      <div className="p-2 bg-blue-100 border border-blue-300 rounded text-sm">
        <p className="font-semibold">{classInfo.subject}</p>
        <p>{classInfo.class} | {classInfo.room}</p>
      </div>
    ) : null;
  };
  
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Lịch giảng dạy</h2>
      
      <div className="bg-white rounded-lg shadow overflow-x-auto">
        <div className="p-4 border-b flex items-center justify-between">
          <div>
            <h3 className="font-semibold">Tháng 5, 2025</h3>
            <p className="text-sm text-gray-500">Tuần 13 (04/05 - 10/05)</p>
          </div>
          <div className="flex space-x-2">
            <button className="px-3 py-1 border rounded text-sm">Tuần trước</button>
            <button className="px-3 py-1 bg-blue-100 border border-blue-300 rounded text-sm">Tuần này</button>
            <button className="px-3 py-1 border rounded text-sm">Tuần sau</button>
          </div>
        </div>
        
        <div className="min-w-max">
          <table className="w-full">
            <thead>
              <tr>
                <th className="p-3 border-b border-r bg-gray-50 w-24"></th>
                {weekdays.map(day => (
                  <th key={day} className="p-3 border-b border-r bg-gray-50 w-44 text-center">{day}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {timeSlots.map(time => (
                <tr key={time}>
                  <td className="p-2 border-b border-r text-center text-sm font-medium bg-gray-50">{time}</td>
                  {weekdays.map(day => (
                    <td key={`${day}-${time}`} className="p-2 border-b border-r">
                      {getClass(day, time)}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      
      <div className="mt-6 bg-white rounded-lg shadow p-6">
        <h3 className="text-lg font-semibold mb-4">Danh sách lớp giảng dạy</h3>
        <table className="w-full">
          <thead>
            <tr className="bg-gray-50">
              <th className="p-3 text-left border-b">Mã lớp</th>
              <th className="p-3 text-left border-b">Tên môn học</th>
              <th className="p-3 text-left border-b">Thời gian</th>
              <th className="p-3 text-left border-b">Phòng học</th>
              <th className="p-3 text-left border-b">Sĩ số</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td className="p-3 border-b">IT505</td>
              <td className="p-3 border-b">Lập trình web nâng cao</td>
              <td className="p-3 border-b">Thứ 2, 7:00-9:30<br/>Thứ 6, 18:00-20:30</td>
              <td className="p-3 border-b">A1.01</td>
              <td className="p-3 border-b">40</td>
            </tr>
            <tr>
              <td className="p-3 border-b">IT202</td>
              <td className="p-3 border-b">Cơ sở dữ liệu</td>
              <td className="p-3 border-b">Thứ 3, 9:45-12:15</td>
              <td className="p-3 border-b">B2.05</td>
              <td className="p-3 border-b">35</td>
            </tr>
            <tr>
              <td className="p-3 border-b">IT303</td>
              <td className="p-3 border-b">Phân tích thiết kế hệ thống</td>
              <td className="p-3 border-b">Thứ 5, 15:15-17:45</td>
              <td className="p-3 border-b">C3.08</td>
              <td className="p-3 border-b">32</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}
// Generate Attendance Code View
function GenerateAttendanceCodeView() {
  const [generatedCode, setGeneratedCode] = useState(null);
  const [selectedClass, setSelectedClass] = useState('');
  const [codeType, setCodeType] = useState('qr');
  const [duration, setDuration] = useState(15);
  
  // Mock class list
  const classList = [
    { id: 'IT505-1', name: 'Lập trình web nâng cao (Thứ 2, 7:00-9:30)' },
    { id: 'IT505-2', name: 'Lập trình web nâng cao (Thứ 6, 18:00-20:30)' },
    { id: 'IT202', name: 'Cơ sở dữ liệu (Thứ 3, 9:45-12:15)' },
    { id: 'IT303', name: 'Phân tích thiết kế hệ thống (Thứ 5, 15:15-17:45)' },
  ];
  
  // Generate random code
  const generateRandomCode = () => {
    return Math.random().toString(36).substring(2, 8).toUpperCase();
  };
  
  // Handle generate code button
  const handleGenerateCode = () => {
    if (!selectedClass) {
      alert('Vui lòng chọn lớp học trước khi tạo mã!');
      return;
    }
    
    const code = generateRandomCode();
    setGeneratedCode({
      code: code,
      class: classList.find(c => c.id === selectedClass).name,
      type: codeType,
      duration: duration,
      expiry: new Date(Date.now() + duration * 60000).toLocaleTimeString(),
    });
  };
  
  // Reset generated code
  const handleResetCode = () => {
    setGeneratedCode(null);
    setSelectedClass('');
  };
  
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Tạo mã điểm danh</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Thiết lập mã điểm danh</h3>
          
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Chọn lớp học</label>
              <select 
                value={selectedClass}
                onChange={(e) => setSelectedClass(e.target.value)}
                className="w-full p-2 border rounded"
                disabled={generatedCode}
              >
                <option value="">-- Chọn lớp học --</option>
                {classList.map(cls => (
                  <option key={cls.id} value={cls.id}>{cls.name}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Loại mã</label>
              <div className="flex space-x-4">
                <label className="flex items-center">
                  <input 
                    type="radio" 
                    value="qr" 
                    checked={codeType === 'qr'} 
                    onChange={() => setCodeType('qr')}
                    disabled={generatedCode}
                  />
                  <span className="ml-2">Mã QR</span>
                </label>
                {/* <label className="flex items-center">
                  <input 
                    type="radio" 
                    value="text" 
                    checked={codeType === 'text'} 
                    onChange={() => setCodeType('text')}
                    disabled={generatedCode}
                  />
                  <span className="ml-2">Mã văn bản</span>
                </label> */}
              </div>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Thời gian hiệu lực (phút)</label>
              <input 
                type="number" 
                value={duration}
                onChange={(e) => setDuration(parseInt(e.target.value))}
                min="1"
                max="60"
                className="w-full p-2 border rounded"
                disabled={generatedCode}
              />
            </div>
            
            <div className="pt-4">
              {!generatedCode ? (
                <button 
                  onClick={handleGenerateCode}
                  className="w-full py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
                >
                  Tạo mã điểm danh
                </button>
              ) : (
                <button 
                  onClick={handleResetCode}
                  className="w-full py-2 bg-gray-600 text-white rounded hover:bg-gray-700 transition-colors"
                >
                  Tạo mã mới
                </button>
              )}
            </div>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Mã điểm danh</h3>
          
          {generatedCode ? (
            <div className="text-center">
              {generatedCode.type === 'qr' ? (
                <div className="mb-4">
                  <div className="mx-auto w-48 h-48 bg-gray-200 flex items-center justify-center">
                    <QrCode size={120} />
                  </div>
                </div>
              ) : (
                <div className="mb-4">
                  <div className="mx-auto w-48 h-48 bg-gray-200 flex items-center justify-center">
                    <p className="text-4xl font-bold">{generatedCode.code}</p>
                  </div>
                </div>
              )}
              
              <div className="p-4 bg-blue-50 rounded-lg">
                <p><span className="font-medium">Lớp:</span> {generatedCode.class}</p>
                <p><span className="font-medium">Mã:</span> {generatedCode.code}</p>
                <p><span className="font-medium">Thời gian hiệu lực:</span> {generatedCode.duration} phút</p>
                <p><span className="font-medium">Hết hạn lúc:</span> {generatedCode.expiry}</p>
              </div>
              
              <div className="mt-4 flex space-x-3">
                <button className="flex-1 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition-colors">
                  Chiếu mã lên màn hình
                </button>
                <button className="py-2 px-3 bg-blue-100 text-blue-600 rounded hover:bg-blue-200 transition-colors">
                  <Download size={20} />
                </button>
              </div>
            </div>
          ) : (
            <div className="h-64 flex items-center justify-center text-gray-500">
              <p>Vui lòng thiết lập và tạo mã điểm danh</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
// Attendance List View
function AttendanceListView() {
const [selectedClass, setSelectedClass] = useState('all');
const [selectedDate, setSelectedDate] = useState('');

// Mock attendance data
const attendanceHistory = [
  { 
    id: 1, 
    class: 'IT505', 
    className: 'Lập trình web nâng cao',
    date: '2025-05-02', 
    time: '7:00-9:30', 
    present: 38, 
    absent: 2, 
    total: 40 
  },
  { 
    id: 2, 
    class: 'IT202', 
    className: 'Cơ sở dữ liệu',
    date: '2025-05-03', 
    time: '9:45-12:15', 
    present: 33, 
    absent: 2, 
    total: 35 
  },
  { 
    id: 3, 
    class: 'IT303', 
    className: 'Phân tích thiết kế hệ thống',
    date: '2025-05-01', 
    time: '15:15-17:45', 
    present: 30, 
    absent: 2, 
    total: 32 
  },
  { 
    id: 4, 
    class: 'IT505', 
    className: 'Lập trình web nâng cao',
    date: '2025-04-29', 
    time: '7:00-9:30', 
    present: 35, 
    absent: 5, 
    total: 40 
  },
];

// Filter attendance data
const filteredAttendance = attendanceHistory.filter(record => {
  const classMatch = selectedClass === 'all' || record.class === selectedClass;
  const dateMatch = !selectedDate || record.date === selectedDate;
  return classMatch && dateMatch;
});

return (
  <div>
    <h2 className="text-2xl font-bold mb-6">Danh sách điểm danh</h2>
    
    <div className="bg-white rounded-lg shadow p-6 mb-6">
      <div className="flex flex-wrap gap-4 mb-6">
        <div className="flex-1 min-w-[200px]">
          <label className="block text-sm font-medium text-gray-700 mb-1">Lớp học</label>
          <select 
            value={selectedClass}
            onChange={(e) => setSelectedClass(e.target.value)}
            className="w-full p-2 border rounded"
          >
            <option value="all">Tất cả lớp</option>
            <option value="IT505">IT505 - Lập trình web nâng cao</option>
            <option value="IT202">IT202 - Cơ sở dữ liệu</option>
            <option value="IT303">IT303 - Phân tích thiết kế hệ thống</option>
          </select>
        </div>
        
        <div className="flex-1 min-w-[200px]">
          <label className="block text-sm font-medium text-gray-700 mb-1">Ngày</label>
          <input 
            type="date" 
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
            className="w-full p-2 border rounded"
          />
        </div>
      </div>
      
      <div className="overflow-x-auto">
        <table className="w-full">
          <thead>
            <tr className="bg-gray-50">
              <th className="py-3 px-4 text-left border-b">ID</th>
              <th className="py-3 px-4 text-left border-b">Lớp học</th>
              <th className="py-3 px-4 text-left border-b">Ngày</th>
              <th className="py-3 px-4 text-left border-b">Thời gian</th>
              <th className="py-3 px-4 text-center border-b">Có mặt</th>
              <th className="py-3 px-4 text-center border-b">Vắng mặt</th>
              <th className="py-3 px-4 text-center border-b">Tỷ lệ</th>
              <th className="py-3 px-4 text-center border-b">Hành động</th>
            </tr>
          </thead>
          <tbody>
            {filteredAttendance.map(record => (
              <tr key={record.id} className="border-b">
                <td className="py-3 px-4">{record.id}</td>
                <td className="py-3 px-4">{record.class} - {record.className}</td>
                <td className="py-3 px-4">{record.date.split('-').reverse().join('/')}</td>
                <td className="py-3 px-4">{record.time}</td>
                <td className="py-3 px-4 text-center text-green-600">{record.present}</td>
                <td className="py-3 px-4 text-center text-red-600">{record.absent}</td>
                <td className="py-3 px-4 text-center">
                  <span 
                    className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                      (record.present / record.total) > 0.8 
                        ? 'bg-green-100 text-green-800' 
                        : 'bg-yellow-100 text-yellow-800'
                    }`}
                  >
                    {Math.round(record.present / record.total * 100)}%
                  </span>
                </td>
                <td className="py-3 px-4 text-center">
                  <div className="flex justify-center space-x-2">
                    <button className="p-1 rounded bg-blue-100 text-blue-600 hover:bg-blue-200">
                      <FileText size={16} />
                    </button>
                    <button className="p-1 rounded bg-green-100 text-green-600 hover:bg-green-200">
                      <Download size={16} />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  </div>
);
}
// Notifications View
function NotificationsView() {
  const [selectedClass, setSelectedClass] = useState('');
  const [notificationTitle, setNotificationTitle] = useState('');
  const [notificationContent, setNotificationContent] = useState('');
  
  // Mock class list
  const classList = [
    { value: 'IT505', label: 'IT505 - Lập trình web nâng cao' },
    { value: 'IT202', label: 'IT202 - Cơ sở dữ liệu' },
    { value: 'IT303', label: 'IT303 - Phân tích thiết kế hệ thống' },
    { value: 'all', label: 'Tất cả lớp học' },
  ];
  
  // Mock notification history
  const notificationHistory = [
    { 
      id: 1, 
      title: 'Thay đổi lịch học ngày 06/05/2025', 
      content: 'Thông báo lớp IT505 sẽ đổi phòng học sang A2.02 vào ngày 06/05/2025.', 
      class: 'IT505', 
      sentAt: '2025-05-03 15:30', 
      recipients: 40 
    },
    { 
      id: 2, 
      title: 'Bài tập về nhà tuần 13', 
      content: 'Sinh viên nộp bài tập về nhà tuần 13 trước ngày 10/05/2025.', 
      class: 'IT202', 
      sentAt: '2025-05-02 10:15', 
      recipients: 35 
    },
    { 
      id: 3, 
      title: 'Thông báo nghỉ học', 
      content: 'Thông báo lớp IT303 sẽ nghỉ học vào ngày 08/05/2025.', 
      class: 'IT303', 
      sentAt: '2025-05-01 17:45', 
      recipients: 32 
    },
  ];
  
  // Handle send notification
  const handleSendNotification = () => {
    if (!selectedClass || !notificationTitle || !notificationContent) {
      alert('Vui lòng điền đầy đủ thông tin!');
      return;
    }
    
    alert('Đã gửi thông báo thành công!');
    setNotificationTitle('');
    setNotificationContent('');
  };
  
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Gửi thông báo</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Soạn thông báo mới</h3>
          
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Lớp nhận thông báo</label>
              <select 
                value={selectedClass}
                onChange={(e) => setSelectedClass(e.target.value)}
                className="w-full p-2 border rounded"
              >
                <option value="">-- Chọn lớp --</option>
                {classList.map(cls => (
                  <option key={cls.value} value={cls.value}>{cls.label}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Tiêu đề</label>
              <input 
                type="text" 
                value={notificationTitle}
                onChange={(e) => setNotificationTitle(e.target.value)}
                className="w-full p-2 border rounded"
                placeholder="Nhập tiêu đề thông báo"
              />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Nội dung</label>
              <textarea 
                value={notificationContent}
                onChange={(e) => setNotificationContent(e.target.value)}
                className="w-full p-2 border rounded h-32"
                placeholder="Nhập nội dung thông báo"
              ></textarea>
            </div>
            
            <div className="pt-4">
              <button 
                onClick={handleSendNotification}
                className="w-full py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
              >
                Gửi thông báo
              </button>
            </div>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Lịch sử thông báo</h3>
          
          <div className="space-y-4">
            {notificationHistory.map(notification => (
              <div key={notification.id} className="border-b pb-4">
                <div className="flex justify-between">
                  <h4 className="font-medium">{notification.title}</h4>
                  <span className="text-xs text-gray-500">{notification.sentAt}</span>
                </div>
                <p className="text-sm mt-1">{notification.content}</p>
                <div className="flex items-center mt-2 text-xs text-gray-600">
                  <span className="mr-4">Lớp: {notification.class}</span>
                  <span>Người nhận: {notification.recipients}</span>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
// Statistics View
function StatisticsView() {
  const [selectedClass, setSelectedClass] = useState('all');
  const [timeRange, setTimeRange] = useState('month');
  
  // Mock class list
  const classList = [
    { id: 'IT505', name: 'Lập trình web nâng cao' },
    { id: 'IT202', name: 'Cơ sở dữ liệu' },
    { id: 'IT303', name: 'Phân tích thiết kế hệ thống' },
  ];
  
  // Mock attendance data
  const attendanceData = {
    overall: 87,
    byClass: {
      'IT505': 92,
      'IT202': 85,
      'IT303': 83
    },
    byWeek: [
      { week: 'Tuần 1', rate: 95 },
      { week: 'Tuần 2', rate: 92 },
      { week: 'Tuần 3', rate: 88 },
      { week: 'Tuần 4', rate: 90 },
      { week: 'Tuần 5', rate: 85 },
      { week: 'Tuần 6', rate: 86 },
      { week: 'Tuần 7', rate: 89 },
      { week: 'Tuần 8', rate: 84 },
      { week: 'Tuần 9', rate: 82 },
      { week: 'Tuần 10', rate: 80 },
      { week: 'Tuần 11', rate: 83 },
      { week: 'Tuần 12', rate: 85 },
      { week: 'Tuần 13', rate: 87 },
    ],
  };
  
  // Mock top students with good attendance
  const topStudents = [
    { id: '20110101', name: 'Nguyễn Văn A', rate: 100, class: 'IT505' },
    { id: '20110102', name: 'Trần Thị B', rate: 100, class: 'IT202' },
    { id: '20110103', name: 'Lê Văn C', rate: 100, class: 'IT303' },
    { id: '20110104', name: 'Phạm Thị D', rate: 96, class: 'IT505' },
    { id: '20110105', name: 'Hoàng Văn E', rate: 96, class: 'IT202' },
  ];
  
  // Mock students with attendance issues
  const attendanceIssues = [
    { id: '20110201', name: 'Phan Văn X', rate: 65, class: 'IT505', absences: 7 },
    { id: '20110202', name: 'Đỗ Thị Y', rate: 62, class: 'IT202', absences: 8 },
    { id: '20110203', name: 'Vũ Văn Z', rate: 58, class: 'IT303', absences: 9 },
    { id: '20110204', name: 'Ngô Thị T', rate: 50, class: 'IT505', absences: 10 },
    { id: '20110205', name: 'Bùi Văn U', rate: 46, class: 'IT202', absences: 11 },
  ];
  
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Thống kê điểm danh</h2>
      
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center space-x-4">
          <label className="flex items-center">
            <span className="mr-2">Lớp:</span>
            <select 
              value={selectedClass}
              onChange={(e) => setSelectedClass(e.target.value)}
              className="p-2 border rounded"
            >
              <option value="all">Tất cả các lớp</option>
              {classList.map(cls => (
                <option key={cls.id} value={cls.id}>{cls.name}</option>
              ))}
            </select>
          </label>
          
          <label className="flex items-center">
            <span className="mr-2">Thời gian:</span>
            <select 
              value={timeRange}
              onChange={(e) => setTimeRange(e.target.value)}
              className="p-2 border rounded"
            >
              <option value="week">Tuần này</option>
              <option value="month">Tháng này</option>
              <option value="semester">Học kỳ</option>
            </select>
          </label>
        </div>
        
        <button className="flex items-center px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
          <Download size={18} />
          <span className="ml-2">Xuất báo cáo</span>
        </button>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
        <div className="bg-white rounded-lg shadow p-6 text-center">
          <p className="text-gray-500 mb-1">Tỷ lệ điểm danh trung bình</p>
          <div className="flex items-center justify-center">
            <div className="relative">
              <svg className="w-32 h-32">
                <circle 
                  className="text-gray-200" 
                  strokeWidth="10" 
                  stroke="currentColor" 
                  fill="transparent" 
                  r="56" 
                  cx="64" 
                  cy="64"
                />
                <circle 
                  className="text-green-500" 
                  strokeWidth="10" 
                  strokeDasharray={`${2 * Math.PI * 56 * attendanceData.overall / 100} ${2 * Math.PI * 56}`} 
                  strokeLinecap="round" 
                  stroke="currentColor" 
                  fill="transparent" 
                  r="56" 
                  cx="64" 
                  cy="64"
                />
              </svg>
              <div className="absolute top-0 left-0 w-full h-full flex items-center justify-center">
                <span className="text-3xl font-bold">{attendanceData.overall}%</span>
              </div>
            </div>
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <p className="text-gray-500 mb-3">Tỷ lệ theo lớp</p>
          <div className="space-y-3">
            {Object.entries(attendanceData.byClass).map(([classId, rate]) => (
              <div key={classId}>
                <div className="flex justify-between mb-1">
                  <span>{classList.find(c => c.id === classId).name}</span>
                  <span>{rate}%</span>
                </div>
                <div className="w-full bg-gray-200 rounded-full h-2">
                  <div 
                    className="bg-blue-600 h-2 rounded-full" 
                    style={{ width: `${rate}%` }}
                  ></div>
                </div>
              </div>
            ))}
          </div>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <p className="text-gray-500 mb-3">Xu hướng điểm danh</p>
          <div className="h-40 flex items-end space-x-1">
            {attendanceData.byWeek.slice(-8).map((week, index) => (
              <div key={index} className="flex-1 flex flex-col items-center">
                <div 
                  className="w-full bg-blue-500 rounded-t" 
                  style={{ height: `${week.rate * 0.4}%` }}
                ></div>
                <span className="text-xs mt-1 transform -rotate-45 origin-top-left">{week.week}</span>
              </div>
            ))}
          </div>
        </div>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Sinh viên điểm danh tốt</h3>
          <table className="w-full">
            <thead>
              <tr className="bg-gray-50">
                <th className="p-3 text-left border-b">MSSV</th>
                <th className="p-3 text-left border-b">Họ và tên</th>
                <th className="p-3 text-left border-b">Lớp</th>
                <th className="p-3 text-center border-b">Tỷ lệ</th>
              </tr>
            </thead>
            <tbody>
              {topStudents.map(student => (
                <tr key={student.id}>
                  <td className="p-3 border-b">{student.id}</td>
                  <td className="p-3 border-b">{student.name}</td>
                  <td className="p-3 border-b">{student.class}</td>
                  <td className="p-3 border-b text-center">
                    <span className="px-2 py-1 bg-green-100 text-green-800 rounded-full text-xs">
                      {student.rate}%
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Sinh viên vắng nhiều</h3>
          <table className="w-full">
            <thead>
              <tr className="bg-gray-50">
                <th className="p-3 text-left border-b">MSSV</th>
                <th className="p-3 text-left border-b">Họ và tên</th>
                <th className="p-3 text-left border-b">Lớp</th>
                <th className="p-3 text-center border-b">Số buổi vắng</th>
                <th className="p-3 text-center border-b">Tỷ lệ</th>
              </tr>
            </thead>
            <tbody>
              {attendanceIssues.map(student => (
                <tr key={student.id}>
                  <td className="p-3 border-b">{student.id}</td>
                  <td className="p-3 border-b">{student.name}</td>
                  <td className="p-3 border-b">{student.class}</td>
                  <td className="p-3 border-b text-center">{student.absences}</td>
                  <td className="p-3 border-b text-center">
                    <span className="px-2 py-1 bg-red-100 text-red-800 rounded-full text-xs">
                      {student.rate}%
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
// Reports View
function ReportsView() {
  const [selectedClass, setSelectedClass] = useState('');
  const [reportType, setReportType] = useState('attendance');
  const [dateRange, setDateRange] = useState('semester');
  const [fileFormat, setFileFormat] = useState('excel');
  const [isGenerating, setIsGenerating] = useState(false);
  
  // Mock class list
  const classList = [
    { id: 'IT505', name: 'Lập trình web nâng cao' },
    { id: 'IT202', name: 'Cơ sở dữ liệu' },
    { id: 'IT303', name: 'Phân tích thiết kế hệ thống' },
    { id: 'all', name: 'Tất cả các lớp' },
  ];
  
  // Mock reports list
  const recentReports = [
    { 
      id: 1, 
      name: 'Báo cáo điểm danh IT505.xlsx', 
      class: 'IT505', 
      date: '01/05/2025', 
      type: 'attendance',
      size: '45 KB'
    },
    { 
      id: 2, 
      name: 'Báo cáo tổng hợp vắng mặt.pdf', 
      class: 'Tất cả', 
      date: '28/04/2025', 
      type: 'absence',
      size: '120 KB'
    },
    { 
      id: 3, 
      name: 'Báo cáo thống kê học kỳ.xlsx', 
      class: 'Tất cả', 
      date: '15/04/2025', 
      type: 'summary',
      size: '250 KB'
    },
  ];
  
  // Handle generate report
  const handleGenerateReport = () => {
    if (!selectedClass) {
      alert('Vui lòng chọn lớp học trước khi tạo báo cáo!');
      return;
    }
    
    setIsGenerating(true);
    
    // Simulate report generation delay
    setTimeout(() => {
      setIsGenerating(false);
      alert('Báo cáo đã được tạo thành công!');
    }, 1500);
  };
  
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Xuất báo cáo</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="md:col-span-1 bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Thiết lập báo cáo</h3>
          
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Loại báo cáo</label>
              <select 
                value={reportType}
                onChange={(e) => setReportType(e.target.value)}
                className="w-full p-2 border rounded"
              >
                <option value="attendance">Báo cáo điểm danh chi tiết</option>
                <option value="absence">Báo cáo vắng mặt</option>
                <option value="summary">Báo cáo tổng hợp</option>
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Lớp học</label>
              <select 
                value={selectedClass}
                onChange={(e) => setSelectedClass(e.target.value)}
                className="w-full p-2 border rounded"
              >
                <option value="">-- Chọn lớp học --</option>
                {classList.map(cls => (
                  <option key={cls.id} value={cls.id}>{cls.name}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Khoảng thời gian</label>
              <select 
                value={dateRange}
                onChange={(e) => setDateRange(e.target.value)}
                className="w-full p-2 border rounded"
              >
                <option value="week">Tuần này</option>
                <option value="month">Tháng này</option>
                <option value="semester">Cả học kỳ</option>
                <option value="custom">Tùy chỉnh</option>
              </select>
            </div>
            
            {dateRange === 'custom' && (
              <div className="grid grid-cols-2 gap-2">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Từ ngày</label>
                  <input 
                    type="date" 
                    className="w-full p-2 border rounded"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Đến ngày</label>
                  <input 
                    type="date" 
                    className="w-full p-2 border rounded"
                  />
                </div>
              </div>
            )}
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Định dạng tệp</label>
              <div className="flex space-x-4">
                <label className="flex items-center">
                  <input 
                    type="radio" 
                    value="excel" 
                    checked={fileFormat === 'excel'} 
                    onChange={() => setFileFormat('excel')}
                  />
                  <span className="ml-2">Excel (.xlsx)</span>
                </label>
                <label className="flex items-center">
                  <input 
                    type="radio" 
                    value="pdf" 
                    checked={fileFormat === 'pdf'} 
                    onChange={() => setFileFormat('pdf')}
                  />
                  <span className="ml-2">PDF</span>
                </label>
              </div>
            </div>
            
            <div className="pt-4">
              <button 
                onClick={handleGenerateReport}
                disabled={isGenerating}
                className={`w-full py-2 ${isGenerating ? 'bg-gray-500' : 'bg-blue-600 hover:bg-blue-700'} text-white rounded transition-colors flex items-center justify-center`}
              >
                {isGenerating ? (
                  <>
                    <span className="animate-spin mr-2">⟳</span>
                    <span>Đang tạo báo cáo...</span>
                  </>
                ) : (
                  <>
                    <Download size={20} className="mr-2" />
                    <span>Tạo báo cáo</span>
                  </>
                )}
              </button>
            </div>
          </div>
        </div>
        
        <div className="md:col-span-2 bg-white rounded-lg shadow p-6">
          <h3 className="text-lg font-semibold mb-4">Báo cáo gần đây</h3>
          
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="bg-gray-50">
                  <th className="p-3 text-left border-b">Tên báo cáo</th>
                  <th className="p-3 text-left border-b">Lớp</th>
                  <th className="p-3 text-left border-b">Ngày tạo</th>
                  <th className="p-3 text-left border-b">Kích thước</th>
                  <th className="p-3 text-center border-b">Thao tác</th>
                </tr>
              </thead>
              <tbody>
                {recentReports.map(report => (
                  <tr key={report.id}>
                    <td className="p-3 border-b">{report.name}</td>
                    <td className="p-3 border-b">{report.class}</td>
                    <td className="p-3 border-b">{report.date}</td>
                    <td className="p-3 border-b">{report.size}</td>
                    <td className="p-3 border-b text-center">
                      <div className="flex justify-center space-x-2">
                        <button className="p-1 text-blue-600 hover:text-blue-800" title="Tải xuống">
                          <Download size={18} />
                        </button>
                        <button className="p-1 text-gray-600 hover:text-gray-800" title="Xem trước">
                          <FileText size={18} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          <div className="mt-6">
            <h3 className="text-lg font-semibold mb-4">Xem trước báo cáo</h3>
            <div className="p-6 border rounded-lg bg-gray-50 flex items-center justify-center h-64">
              {selectedClass ? (
                <div className="text-center">
                  <FileText size={48} className="mx-auto text-gray-400 mb-2" />
                  <p>Nhấn "Tạo báo cáo" để xem trước</p>
                </div>
              ) : (
                <p className="text-gray-500">Vui lòng chọn lớp và loại báo cáo để xem trước</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
// Edit Attendance View
function EditAttendanceView() {
  
  const [selectedClass, setSelectedClass] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [attendanceData, setAttendanceData] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  
  // Mock class list
  const classList = [
    { id: 'IT505', name: 'Lập trình web nâng cao' },
    { id: 'IT202', name: 'Cơ sở dữ liệu' },
    { id: 'IT303', name: 'Phân tích thiết kế hệ thống' },
  ];
  
  // Mock class dates (past sessions)
  const classDates = [
    { id: '20250502', date: '2025-05-02' },
    { id: '20250425', date: '2025-04-25' },
    { id: '20250418', date: '2025-04-18' },
    { id: '20250411', date: '2025-04-11' },
  ];
  
  // Mock student attendance data
  const mockAttendanceData = [
    { id: '20110123', name: 'Nguyễn Văn B', status: 'present', notes: '' },
    { id: '20110124', name: 'Lê Thị D', status: 'absent', notes: '' },
    { id: '20110125', name: 'Trần Văn E', status: 'excused', notes: 'Đơn xin phép được duyệt' },
    { id: '20110126', name: 'Phạm Thị F', status: 'present', notes: '' },
    { id: '20110127', name: 'Hoàng Văn G', status: 'present', notes: '' },
    { id: '20110128', name: 'Vũ Thị H', status: 'late', notes: 'Đến trễ 15 phút' },
    { id: '20110129', name: 'Đặng Văn I', status: 'present', notes: '' },
    { id: '20110130', name: 'Bùi Thị J', status: 'absent', notes: '' },
    { id: '20110131', name: 'Lý Văn K', status: 'present', notes: '' },
    { id: '20110132', name: 'Huỳnh Thị L', status: 'present', notes: '' }
  ];
  
  // Load attendance data when class and date are selected
  const handleLoadAttendance = () => {
    if (!selectedClass || !selectedDate) {
      alert('Vui lòng chọn lớp học và ngày để tiếp tục');
      return;
    }
    
    setIsLoading(true);
    
    // Simulate loading data
    setTimeout(() => {
      setAttendanceData(mockAttendanceData);
      setIsLoading(false);
    }, 500);
  };
  
  // Change student attendance status
  const changeAttendanceStatus = (studentId, newStatus) => {
    setAttendanceData(prevData => 
      prevData.map(student => 
        student.id === studentId 
          ? { ...student, status: newStatus } 
          : student
      )
    );
  };
  
  // Update student notes
  const updateStudentNotes = (studentId, notes) => {
    setAttendanceData(prevData => 
      prevData.map(student => 
        student.id === studentId 
          ? { ...student, notes: notes } 
          : student
      )
    );
  };
  
  // Filter students based on search query
  const filteredStudents = attendanceData.filter(student => 
    student.name.toLowerCase().includes(searchQuery.toLowerCase()) || 
    student.id.includes(searchQuery)
  );
  
  // Save attendance changes
  const saveAttendanceChanges = () => {
    alert('Thay đổi điểm danh đã được lưu thành công!');
  };
  
  // Status badge and dropdown component
  const StatusSelector = ({ studentId, currentStatus }) => {
    const statusOptions = [
      { value: 'present', label: 'Có mặt', color: 'green' },
      { value: 'absent', label: 'Vắng mặt', color: 'red' },
      { value: 'excused', label: 'Có phép', color: 'blue' },
      { value: 'late', label: 'Đi trễ', color: 'yellow' }
    ];
    
    const [isOpen, setIsOpen] = useState(false);
    
    const currentOption = statusOptions.find(option => option.value === currentStatus);
    
    return (
      <div className="relative">
        <button
          onClick={() => setIsOpen(!isOpen)}
          className={`inline-flex items-center px-3 py-1 rounded text-sm font-medium bg-${currentOption.color}-100 text-${currentOption.color}-800 hover:bg-${currentOption.color}-200`}
        >
          {currentOption.label}
          <ChevronDown size={16} className="ml-1" />
        </button>
        
        {isOpen && (
          <div className="absolute top-full left-0 mt-1 w-36 bg-white border rounded-md shadow-lg z-10">
            {statusOptions.map(option => (
              <button
                key={option.value}
                onClick={() => {
                  changeAttendanceStatus(studentId, option.value);
                  setIsOpen(false);
                }}
                className={`block w-full text-left px-4 py-2 text-sm hover:bg-gray-100 ${
                  currentStatus === option.value ? 'bg-gray-50 font-medium' : ''
                }`}
              >
                {option.label}
              </button>
            ))}
          </div>
        )}
      </div>
    );
  };
  
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Điều chỉnh trạng thái điểm danh</h2>
      
      <div className="bg-white rounded-lg shadow p-6 mb-6">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Chọn lớp học</label>
            <select 
              value={selectedClass}
              onChange={(e) => setSelectedClass(e.target.value)}
              className="w-full p-2 border rounded"
              disabled={isLoading}
            >
              <option value="">-- Chọn lớp học --</option>
              {classList.map(cls => (
                <option key={cls.id} value={cls.id}>{cls.id} - {cls.name}</option>
              ))}
            </select>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Chọn buổi học</label>
            <select 
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
              className="w-full p-2 border rounded"
              disabled={isLoading}
            >
              <option value="">-- Chọn ngày --</option>
              {classDates.map(date => (
                <option key={date.id} value={date.date}>
                  {date.date.split('-').reverse().join('/')}
                </option>
              ))}
            </select>
          </div>
          
          <div className="flex items-end">
            <button
              onClick={handleLoadAttendance}
              disabled={!selectedClass || !selectedDate || isLoading}
              className={`w-full p-2 rounded text-white ${
                !selectedClass || !selectedDate || isLoading
                  ? 'bg-gray-400 cursor-not-allowed'
                  : 'bg-blue-600 hover:bg-blue-700'
              }`}
            >
              {isLoading ? 'Đang tải...' : 'Tải dữ liệu điểm danh'}
            </button>
          </div>
        </div>
        
        {attendanceData.length > 0 && (
          <>
            <div className="relative mb-6">
              <Search className="absolute left-3 top-2.5 text-gray-400" size={20} />
              <input
                type="text"
                placeholder="Tìm kiếm sinh viên..."
                className="w-full pl-10 pr-4 py-2 border rounded-lg"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </div>
            
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead>
                  <tr className="bg-gray-50">
                    <th className="py-3 px-4 text-left border-b">MSSV</th>
                    <th className="py-3 px-4 text-left border-b">Họ và tên</th>
                    <th className="py-3 px-4 text-center border-b">Trạng thái</th>
                    <th className="py-3 px-4 text-left border-b">Ghi chú</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredStudents.map(student => (
                    <tr key={student.id} className="border-b">
                      <td className="py-3 px-4">{student.id}</td>
                      <td className="py-3 px-4">{student.name}</td>
                      <td className="py-3 px-4 text-center">
                        <StatusSelector 
                          studentId={student.id} 
                          currentStatus={student.status} 
                        />
                      </td>
                      <td className="py-3 px-4">
                        <input
                          type="text"
                          value={student.notes}
                          onChange={(e) => updateStudentNotes(student.id, e.target.value)}
                          placeholder="Thêm ghi chú..."
                          className="w-full p-1 border border-gray-300 rounded"
                        />
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            
            <div className="flex justify-between items-center mt-6">
              <div className="text-sm text-gray-600">
                Hiển thị {filteredStudents.length} trong tổng số {attendanceData.length} sinh viên
              </div>
              
              <div className="flex space-x-3">
                <button 
                  onClick={saveAttendanceChanges}
                  className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition-colors"
                >
                  Lưu thay đổi
                </button>
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
// Manual Attendance View
function ManualAttendanceView() {
  const [selectedClass, setSelectedClass] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedSession, setSelectedSession] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [attendanceData, setAttendanceData] = useState([]);
  const [showAttendanceList, setShowAttendanceList] = useState(false);
  
  // Mock class list
  const classList = [
    { id: 'IT505-1', name: 'Lập trình web nâng cao (Thứ 2, 7:00-9:30)' },
    { id: 'IT505-2', name: 'Lập trình web nâng cao (Thứ 6, 18:00-20:30)' },
    { id: 'IT202', name: 'Cơ sở dữ liệu (Thứ 3, 9:45-12:15)' },
    { id: 'IT303', name: 'Phân tích thiết kế hệ thống (Thứ 5, 15:15-17:45)' },
  ];
  
  // Mock session list - will be dynamic based on class selection
  const sessionList = [
    { id: '1', name: 'Buổi 1 (01/03/2025)' },
    { id: '2', name: 'Buổi 2 (08/03/2025)' },
    { id: '3', name: 'Buổi 3 (15/03/2025)' },
    { id: '4', name: 'Buổi 4 (22/03/2025)' },
    { id: '5', name: 'Buổi 5 (29/03/2025)' },
    { id: '6', name: 'Buổi 6 (05/04/2025)' },
    { id: '7', name: 'Buổi 7 (12/04/2025)' },
    { id: '8', name: 'Buổi 8 (19/04/2025)' },
    { id: '9', name: 'Buổi 9 (26/04/2025)' },
    { id: '10', name: 'Buổi 10 (03/05/2025)' },
  ];
  
  // Mock student data
  const mockStudents = [
    { id: '20110123', name: 'Nguyễn Văn B', status: 'present' },
    { id: '20110456', name: 'Trần Thị C', status: 'absent' },
    { id: '20110789', name: 'Lê Văn D', status: 'late' },
    { id: '20110234', name: 'Phạm Thị E', status: 'present' },
    { id: '20110567', name: 'Hoàng Văn F', status: 'present' },
    { id: '20110890', name: 'Đỗ Thị G', status: 'absent' },
    { id: '20110111', name: 'Vũ Văn H', status: 'present' },
    { id: '20110222', name: 'Đinh Thị I', status: 'late' },
    { id: '20110333', name: 'Bùi Văn J', status: 'present' },
    { id: '20110444', name: 'Lý Thị K', status: 'present' },
    { id: '20110555', name: 'Ngô Văn L', status: 'absent' },
    { id: '20110666', name: 'Mai Thị M', status: 'present' },
    { id: '20110777', name: 'Trịnh Văn N', status: 'present' },
    { id: '20110888', name: 'Cao Thị O', status: 'late' },
    { id: '20110999', name: 'Đặng Văn P', status: 'present' },
  ];
  
  // Load attendance data when class and session are selected
  const handleLoadAttendance = () => {
    if (!selectedClass || !selectedSession) {
      alert('Vui lòng chọn lớp học và buổi học!');
      return;
    }
    
    // In a real app, this would fetch data from the server
    setAttendanceData([...mockStudents]);
    setShowAttendanceList(true);
  };
  
  // Update student attendance status
  const updateAttendanceStatus = (studentId, newStatus) => {
    setAttendanceData(attendanceData.map(student => 
      student.id === studentId ? { ...student, status: newStatus } : student
    ));
  };
  
  // Save attendance data
  const handleSaveAttendance = () => {
    // In a real app, this would send data to the server
    alert('Đã lưu thông tin điểm danh thành công!');
  };
  
  // Filter students based on search query
  const filteredStudents = attendanceData.filter(student => 
    student.name.toLowerCase().includes(searchQuery.toLowerCase()) || 
    student.id.includes(searchQuery)
  );
  
  // Get count of each status
  const getStatusCount = (status) => {
    return attendanceData.filter(student => student.status === status).length;
  };
  
  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Điểm danh thủ công</h2>
      
      <div className="bg-white rounded-lg shadow p-6 mb-6">
        <h3 className="text-lg font-semibold mb-4">Chọn lớp và buổi học</h3>
        
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Lớp học</label>
            <select 
              value={selectedClass}
              onChange={(e) => setSelectedClass(e.target.value)}
              className="w-full p-2 border rounded"
            >
              <option value="">-- Chọn lớp học --</option>
              {classList.map(cls => (
                <option key={cls.id} value={cls.id}>{cls.name}</option>
              ))}
            </select>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Ngày điểm danh</label>
            <input 
              type="date" 
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
              className="w-full p-2 border rounded"
            />
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Buổi học</label>
            <select 
              value={selectedSession}
              onChange={(e) => setSelectedSession(e.target.value)}
              className="w-full p-2 border rounded"
              disabled={!selectedClass}
            >
              <option value="">-- Chọn buổi học --</option>
              {sessionList.map(session => (
                <option key={session.id} value={session.id}>{session.name}</option>
              ))}
            </select>
          </div>
        </div>
        
        <div className="flex justify-end">
          <button 
            onClick={handleLoadAttendance}
            className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
          >
            Tải danh sách
          </button>
        </div>
      </div>
      
      {showAttendanceList && (
        <div className="bg-white rounded-lg shadow">
          <div className="p-4 border-b flex items-center justify-between">
            <div>
              <h3 className="font-semibold">
                {classList.find(c => c.id === selectedClass)?.name} | 
                {sessionList.find(s => s.id === selectedSession)?.name}
              </h3>
              <div className="text-sm text-gray-500 mt-1 flex space-x-4">
                <span className="flex items-center">
                  <div className="w-3 h-3 rounded-full bg-green-500 mr-1"></div>
                  Có mặt: {getStatusCount('present')}
                </span>
                <span className="flex items-center">
                  <div className="w-3 h-3 rounded-full bg-red-500 mr-1"></div>
                  Vắng mặt: {getStatusCount('absent')}
                </span>
                <span className="flex items-center">
                  <div className="w-3 h-3 rounded-full bg-yellow-500 mr-1"></div>
                  Đi trễ: {getStatusCount('late')}
                </span>
              </div>
            </div>
            <div className="relative">
              <input 
                type="text" 
                placeholder="Tìm kiếm sinh viên..." 
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="p-2 pl-8 border rounded w-64"
              />
              <Search className="absolute left-2 top-2.5 text-gray-400" size={16} />
            </div>
          </div>
          
          <div className="p-4">
            <table className="w-full">
              <thead>
                <tr className="bg-gray-50">
                  <th className="p-2 text-left border-b">MSSV</th>
                  <th className="p-2 text-left border-b">Họ tên</th>
                  <th className="p-2 text-center border-b">Trạng thái</th>
                  <th className="p-2 text-right border-b">Thao tác</th>
                </tr>
              </thead>
              <tbody>
                {filteredStudents.map(student => (
                  <tr key={student.id}>
                    <td className="p-2 border-b">{student.id}</td>
                    <td className="p-2 border-b">{student.name}</td>
                    <td className="p-2 border-b text-center">
                      <span className={`inline-block w-24 py-1 rounded-full text-xs font-medium text-center ${
                        student.status === 'present' ? 'bg-green-100 text-green-800' :
                        student.status === 'absent' ? 'bg-red-100 text-red-800' :
                        'bg-yellow-100 text-yellow-800'
                      }`}>
                        {student.status === 'present' ? 'Có mặt' :
                         student.status === 'absent' ? 'Vắng mặt' : 'Đi trễ'}
                      </span>
                    </td>
                    <td className="p-2 border-b text-right">
                      <div className="flex justify-end space-x-1">
                        <button 
                          onClick={() => updateAttendanceStatus(student.id, 'present')}
                          className={`p-1 rounded ${student.status === 'present' ? 'bg-green-100 text-green-600' : 'hover:bg-gray-100'}`}
                        >
                          <UserCheck size={18} />
                        </button>
                        <button 
                          onClick={() => updateAttendanceStatus(student.id, 'absent')}
                          className={`p-1 rounded ${student.status === 'absent' ? 'bg-red-100 text-red-600' : 'hover:bg-gray-100'}`}
                        >
                          <UserX size={18} />
                        </button>
                        <button 
                          onClick={() => updateAttendanceStatus(student.id, 'late')}
                          className={`p-1 rounded ${student.status === 'late' ? 'bg-yellow-100 text-yellow-600' : 'hover:bg-gray-100'}`}
                        >
                          <Clock size={18} />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          
          <div className="p-4 border-t flex justify-between">
            <div>
              <span className="text-sm text-gray-500">Hiển thị {filteredStudents.length} trên tổng số {attendanceData.length} sinh viên</span>
            </div>
            <button 
              onClick={handleSaveAttendance}
              className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition-colors"
            >
              Lưu điểm danh
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
// Absence Requests View
function AbsenceRequestsView() {
  const [activeTab, setActiveTab] = useState('pending');
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedClass, setSelectedClass] = useState('all');
  const [sortBy, setSortBy] = useState('date');
  const [sortDirection, setSortDirection] = useState('desc');
  
  // Mock data for absence requests
  const mockAbsenceRequests = [
    {
      id: 1,
      studentId: '20110123',
      studentName: 'Nguyễn Văn B',
      className: 'IT505',
      date: '03/05/2025',
      reason: 'Lý do sức khỏe, đã đính kèm giấy khám bệnh',
      status: 'pending',
      submittedDate: '01/05/2025'
    },
    {
      id: 2,
      studentId: '20110456',
      studentName: 'Trần Thị C',
      className: 'IT202',
      date: '04/05/2025',
      reason: 'Tham gia cuộc thi học thuật của trường',
      status: 'pending',
      submittedDate: '02/05/2025'
    },
    {
      id: 3,
      studentId: '20110789',
      studentName: 'Lê Văn D',
      className: 'IT303',
      date: '05/05/2025',
      reason: 'Đi công tác cùng công ty, có xác nhận từ quản lý',
      status: 'pending',
      submittedDate: '03/05/2025'
    },
    {
      id: 4,
      studentId: '20110234',
      studentName: 'Phạm Thị E',
      className: 'IT404',
      date: '01/05/2025',
      reason: 'Lý do gia đình',
      status: 'approved',
      submittedDate: '29/04/2025'
    },
    {
      id: 5,
      studentId: '20110567',
      studentName: 'Hoàng Văn F',
      className: 'IT505',
      date: '02/05/2025',
      reason: 'Tham gia hoạt động tình nguyện của Đoàn trường',
      status: 'rejected',
      submittedDate: '30/04/2025'
    }
  ];

  // Mock data for class list
  const classList = [
    { id: 'all', name: 'Tất cả lớp' },
    { id: 'IT505', name: 'IT505 - Lập trình web nâng cao' },
    { id: 'IT202', name: 'IT202 - Cơ sở dữ liệu' },
    { id: 'IT303', name: 'IT303 - Phân tích thiết kế hệ thống' },
    { id: 'IT404', name: 'IT404 - Trí tuệ nhân tạo' }
  ];

  // Filter requests based on search, class, and active tab
  const filteredRequests = mockAbsenceRequests
    .filter(request => 
      (activeTab === 'all' || request.status === activeTab) &&
      (selectedClass === 'all' || request.className === selectedClass) &&
      (request.studentName.toLowerCase().includes(searchTerm.toLowerCase()) || 
       request.studentId.includes(searchTerm) ||
       request.className.toLowerCase().includes(searchTerm.toLowerCase()))
    )
    .sort((a, b) => {
      if (sortBy === 'date') {
        const dateA = new Date(a.date.split('/').reverse().join('/'));
        const dateB = new Date(b.date.split('/').reverse().join('/'));
        return sortDirection === 'asc' ? dateA - dateB : dateB - dateA;
      } else if (sortBy === 'name') {
        return sortDirection === 'asc' 
          ? a.studentName.localeCompare(b.studentName) 
          : b.studentName.localeCompare(a.studentName);
      } else if (sortBy === 'class') {
        return sortDirection === 'asc'
          ? a.className.localeCompare(b.className)
          : b.className.localeCompare(a.className);
      }
      return 0;
    });

  // Handle request action (approve/reject)
  const handleRequestAction = (id, action) => {
    // In a real app, this would be an API call
    console.log(`Request ${id} ${action}`);
    // Then update the local state or refetch data
  };

  // Toggle sort direction
  const handleSort = (column) => {
    if (sortBy === column) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setSortBy(column);
      setSortDirection('asc');
    }
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Đơn xin phép vắng</h2>
      
      {/* Filters and search */}
      <div className="mb-6 flex flex-col md:flex-row md:items-center md:justify-between gap-4">
        <div className="flex flex-col md:flex-row md:items-center gap-4">
          {/* Tab selector */}
          <div className="inline-flex rounded-md shadow-sm" role="group">
            <button
              type="button"
              onClick={() => setActiveTab('all')}
              className={`px-4 py-2 text-sm font-medium rounded-l-lg ${
                activeTab === 'all' 
                  ? 'bg-blue-600 text-white' 
                  : 'bg-white text-gray-700 hover:bg-gray-50'
              }`}
            >
              Tất cả
            </button>
            <button
              type="button"
              onClick={() => setActiveTab('pending')}
              className={`px-4 py-2 text-sm font-medium ${
                activeTab === 'pending' 
                  ? 'bg-blue-600 text-white' 
                  : 'bg-white text-gray-700 hover:bg-gray-50'
              }`}
            >
              Chờ duyệt
            </button>
            <button
              type="button"
              onClick={() => setActiveTab('approved')}
              className={`px-4 py-2 text-sm font-medium ${
                activeTab === 'approved' 
                  ? 'bg-blue-600 text-white' 
                  : 'bg-white text-gray-700 hover:bg-gray-50'
              }`}
            >
              Đã duyệt
            </button>
            <button
              type="button"
              onClick={() => setActiveTab('rejected')}
              className={`px-4 py-2 text-sm font-medium rounded-r-lg ${
                activeTab === 'rejected' 
                  ? 'bg-blue-600 text-white' 
                  : 'bg-white text-gray-700 hover:bg-gray-50'
              }`}
            >
              Đã từ chối
            </button>
          </div>
          
          {/* Class selector */}
          <select
            className="bg-white border border-gray-300 text-gray-700 rounded-lg px-3 py-2 text-sm"
            value={selectedClass}
            onChange={(e) => setSelectedClass(e.target.value)}
          >
            {classList.map(classItem => (
              <option key={classItem.id} value={classItem.id}>
                {classItem.name}
              </option>
            ))}
          </select>
        </div>
        
        {/* Search */}
        <div className="relative">
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <Search size={18} className="text-gray-400" />
          </div>
          <input
            type="text"
            className="bg-white border border-gray-300 text-gray-700 pl-10 pr-4 py-2 rounded-lg w-full md:w-64"
            placeholder="Tìm kiếm sinh viên, mã số..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </div>
      
      {/* Requests table */}
      <div className="bg-white rounded-lg shadow overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th 
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                  onClick={() => handleSort('name')}
                >
                  <div className="flex items-center">
                    Sinh viên
                    {sortBy === 'name' && (
                      <ChevronDown 
                        size={16} 
                        className={`ml-1 transform ${sortDirection === 'asc' ? 'rotate-180' : ''}`} 
                      />
                    )}
                  </div>
                </th>
                <th 
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                  onClick={() => handleSort('class')}
                >
                  <div className="flex items-center">
                    Lớp
                    {sortBy === 'class' && (
                      <ChevronDown 
                        size={16} 
                        className={`ml-1 transform ${sortDirection === 'asc' ? 'rotate-180' : ''}`} 
                      />
                    )}
                  </div>
                </th>
                <th 
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                  onClick={() => handleSort('date')}
                >
                  <div className="flex items-center">
                    Ngày vắng
                    {sortBy === 'date' && (
                      <ChevronDown 
                        size={16} 
                        className={`ml-1 transform ${sortDirection === 'asc' ? 'rotate-180' : ''}`} 
                      />
                    )}
                  </div>
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Lý do
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Ngày nộp
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Trạng thái
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Thao tác
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {filteredRequests.length > 0 ? (
                filteredRequests.map(request => (
                  <tr key={request.id}>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm font-medium text-gray-900">{request.studentName}</div>
                      <div className="text-sm text-gray-500">{request.studentId}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900">{request.className}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900">{request.date}</div>
                    </td>
                    <td className="px-6 py-4">
                      <div className="text-sm text-gray-900 max-w-xs">
                        {request.reason}
                        {/* File attachment link would go here */}
                        <button className="text-blue-600 hover:underline ml-2 text-xs">
                          Xem đính kèm
                        </button>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900">{request.submittedDate}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                        ${request.status === 'pending' ? 'bg-yellow-100 text-yellow-800' : ''}
                        ${request.status === 'approved' ? 'bg-green-100 text-green-800' : ''}
                        ${request.status === 'rejected' ? 'bg-red-100 text-red-800' : ''}
                      `}>
                        {request.status === 'pending' && 'Chờ duyệt'}
                        {request.status === 'approved' && 'Đã duyệt'}
                        {request.status === 'rejected' && 'Đã từ chối'}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      {request.status === 'pending' && (
                        <div className="flex space-x-2">
                          <button 
                            onClick={() => handleRequestAction(request.id, 'approve')}
                            className="text-green-600 hover:text-green-900"
                          >
                            Duyệt
                          </button>
                          <button 
                            onClick={() => handleRequestAction(request.id, 'reject')}
                            className="text-red-600 hover:text-red-900"
                          >
                            Từ chối
                          </button>
                        </div>
                      )}
                      {(request.status === 'approved' || request.status === 'rejected') && (
                        <button className="text-blue-600 hover:text-blue-900">
                          Chi tiết
                        </button>
                      )}
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="7" className="px-6 py-4 text-center text-sm text-gray-500">
                    Không có đơn xin phép nào phù hợp với điều kiện tìm kiếm
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        {/* Pagination */}
        <div className="px-6 py-4 bg-white border-t border-gray-200 flex items-center justify-between">
          <div className="flex-1 flex justify-between md:hidden">
            <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
              Trang trước
            </button>
            <button className="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
              Trang sau
            </button>
          </div>
          <div className="hidden md:flex-1 md:flex md:items-center md:justify-between">
            <div>
              <p className="text-sm text-gray-700">
                Hiển thị <span className="font-medium">1</span> đến <span className="font-medium">{filteredRequests.length}</span> của <span className="font-medium">{filteredRequests.length}</span> kết quả
              </p>
            </div>
            <div>
              <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                <button className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                  <span className="sr-only">Previous</span>
                  <ChevronDown className="h-5 w-5 transform rotate-90" />
                </button>
                <button className="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                  1
                </button>
                <button className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                  <span className="sr-only">Next</span>
                  <ChevronDown className="h-5 w-5 transform -rotate-90" />
                </button>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}