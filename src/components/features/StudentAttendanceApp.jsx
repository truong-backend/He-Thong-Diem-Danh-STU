import { useState, useEffect } from 'react';
import { 
  Calendar, 
  Clock, 
  BookOpen, 
  CheckSquare, 
  FileText, 
  BarChart2, 
  Bell, 
  MessageSquare,
  User,
  LogOut,
  Menu,
  X,
  ChevronDown
} from 'lucide-react';

// Main App component
const StudentAttendanceApp = () => {
  const [activeTab, setActiveTab] = useState('timetable');
  const [notifications, setNotifications] = useState([
    { id: 1, title: 'Thông báo học môn Lập trình nâng cao', content: 'Buổi học ngày mai sẽ bắt đầu sớm 15 phút', time: '9:30 AM', isRead: false },
    { id: 2, title: 'Nhắc nhở điểm danh', content: 'Bạn sắp vào học môn Hệ thống thông tin', time: '1:15 PM', isRead: false },
    { id: 3, title: 'Thông báo đơn xin phép', content: 'Đơn xin phép của bạn đã được chấp nhận', time: '11:45 AM', isRead: true },
  ]);
  
  const [studentInfo] = useState({
    id: 'SV001234',
    name: 'Nguyễn Văn A',
    class: 'CNTT2023',
    email: 'nguyenvana@student.saigontech.edu.vn',
    phone: '0901234567',
    avatar: '/api/placeholder/150/150'
  });

  // Mobile menu state
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  
  // Close mobile menu when changing tabs
  useEffect(() => {
    setMobileMenuOpen(false);
  }, [activeTab]);
  
  // Course data
  const courses = [
    { 
      id: 'CSC001', 
      name: 'Lập trình nâng cao', 
      teacher: 'Nguyễn Văn B', 
      room: 'A303',
      attendance: 85,
      total: 9,
      attended: 7,
      absent: 1,
      excused: 1
    },
    { 
      id: 'CSC002', 
      name: 'Hệ thống thông tin', 
      teacher: 'Trần Thị C', 
      room: 'B201',
      attendance: 92,
      total: 12,
      attended: 11,
      absent: 0,
      excused: 1
    },
    { 
      id: 'CSC003', 
      name: 'Cơ sở dữ liệu', 
      teacher: 'Lê Văn D', 
      room: 'C105',
      attendance: 75,
      total: 8,
      attended: 6,
      absent: 2,
      excused: 0
    },
  ];
  
  // Timetable data
  const timetable = [
    { 
      day: 'Thứ 2', 
      classes: [
        { id: 1, courseId: 'CSC001', name: 'Lập trình nâng cao', time: '7:30 - 10:30', room: 'A303' }
      ] 
    },
    { 
      day: 'Thứ 3', 
      classes: [
        { id: 2, courseId: 'CSC002', name: 'Hệ thống thông tin', time: '13:00 - 16:00', room: 'B201' }
      ] 
    },
    { 
      day: 'Thứ 5', 
      classes: [
        { id: 3, courseId: 'CSC003', name: 'Cơ sở dữ liệu', time: '7:30 - 11:30', room: 'C105' }
      ] 
    },
  ];
  
  // Course session attendance data
  const coursesWithSessions = [
    {
      id: 'CSC001',
      name: 'Lập trình nâng cao',
      sessions: [
        { id: 1, date: '06/04/2025', name: 'Buổi 1', time: '7:30', status: 'present', method: 'QR Code' },
        { id: 2, date: '13/04/2025', name: 'Buổi 2', time: '7:33', status: 'present', method: 'GPS' },
        { id: 3, date: '20/04/2025', name: 'Buổi 3', time: '7:35', status: 'present', method: 'QR Code' },
        { id: 4, date: '27/04/2025', name: 'Buổi 4', time: '', status: 'excused', method: 'Đơn xin phép' },
      ]
    },
    {
      id: 'CSC002',
      name: 'Hệ thống thông tin',
      sessions: [
        { id: 1, date: '07/04/2025', name: 'Buổi 1', time: '13:01', status: 'present', method: 'Mã điểm danh' },
        { id: 2, date: '14/04/2025', name: 'Buổi 2', time: '13:05', status: 'present', method: 'GPS' },
        { id: 3, date: '21/04/2025', name: 'Buổi 3', time: '13:05', status: 'present', method: 'GPS' },
        { id: 4, date: '28/04/2025', name: 'Buổi 4', time: '13:02', status: 'present', method: 'Mã điểm danh' },
      ]
    },
    {
      id: 'CSC003',
      name: 'Cơ sở dữ liệu',
      sessions: [
        { id: 1, date: '09/04/2025', name: 'Buổi 1', time: '7:32', status: 'present', method: 'QR Code' },
        { id: 2, date: '16/04/2025', name: 'Buổi 2', time: '7:35', status: 'present', method: 'GPS' },
        { id: 3, date: '23/04/2025', name: 'Buổi 3', time: '', status: 'absent', method: '' },
        { id: 4, date: '30/04/2025', name: 'Buổi 4', time: '7:33', status: 'present', method: 'QR Code' },
      ]
    }
  ];
  
  // Helper functions
  const getStatusColor = (status) => {
    switch(status) {
      case 'present': return 'text-green-600';
      case 'absent': return 'text-red-600';
      case 'excused': return 'text-yellow-600';
      default: return 'text-gray-600';
    }
  };
  
  const getStatusText = (status) => {
    switch(status) {
      case 'present': return 'Có mặt';
      case 'absent': return 'Vắng mặt';
      case 'excused': return 'Có phép';
      default: return '';
    }
  };

  // State for expanded course in history tab
  const [expandedCourse, setExpandedCourse] = useState(null);
  
  // Navigation items for sidebar and mobile menu
  const navItems = [
    { id: 'timetable', title: 'Thời khóa biểu', icon: <Calendar size={20} /> },
    { id: 'attendance', title: 'Điểm danh', icon: <CheckSquare size={20} /> },
    { id: 'history', title: 'Lịch sử điểm danh', icon: <BookOpen size={20} /> },
    { id: 'statistics', title: 'Thống kê điểm danh', icon: <BarChart2 size={20} /> },
    { id: 'absence', title: 'Đơn xin phép', icon: <FileText size={20} /> },
    { id: 'notifications', title: 'Thông báo', icon: <Bell size={20} />, badge: notifications.filter(n => !n.isRead).length },
    { id: 'messages', title: 'Thông báo từ GV', icon: <MessageSquare size={20} /> },
    { id: 'profile', title: 'Thông tin cá nhân', icon: <User size={20} /> },
  ];

  // Render different content based on active tab
  const renderContent = () => {
    switch(activeTab) {
      case 'timetable':
        return (
          <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Thời khóa biểu</h2>
            <div className="space-y-4">
              {timetable.map((day, index) => (
                <div key={index} className="bg-white rounded-lg shadow p-4">
                  <h3 className="font-semibold text-lg text-blue-700 mb-2">{day.day}</h3>
                  {day.classes.length > 0 ? (
                    day.classes.map((cls) => (
                      <div key={cls.id} className="border-l-4 border-blue-500 pl-3 py-2">
                        <div className="font-medium">{cls.name}</div>
                        <div className="text-sm text-gray-600 flex items-center mt-1">
                          <Clock size={16} className="mr-1" /> {cls.time}
                        </div>
                        <div className="text-sm text-gray-600 mt-1">Phòng: {cls.room}</div>
                      </div>
                    ))
                  ) : (
                    <p className="text-gray-500 italic">Không có lịch học</p>
                  )}
                </div>
              ))}
            </div>
          </div>
        );
        
      case 'attendance':
        return (
          <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Điểm danh</h2>
            
            <div className="bg-white rounded-lg shadow p-4 mb-6">
              <h3 className="font-semibold text-lg text-blue-700 mb-3">Buổi học hiện tại</h3>
              <div className="border-l-4 border-green-500 pl-3 py-2">
                <div className="font-medium">Hệ thống thông tin (CSC002)</div>
                <div className="text-sm text-gray-600 flex items-center mt-1">
                  <Clock size={16} className="mr-1" /> 13:00 - 16:00
                </div>
                <div className="text-sm text-gray-600 mt-1">Phòng: B201</div>
                <div className="text-sm text-gray-600 mt-1">Giảng viên: Trần Thị C</div>
              </div>
              
              <div className="mt-4">
                <h4 className="font-medium mb-2">Chọn phương thức điểm danh:</h4>
                <div className="grid grid-cols-2 sm:grid-cols-3 gap-3">
                  <button className="bg-blue-500 text-white py-3 px-4 rounded-lg flex flex-col items-center justify-center">
                    <div className="text-2xl mb-1">📱</div>
                    <div className="text-sm">Quét mã QR</div>
                  </button>
                  <button className="bg-green-500 text-white py-3 px-4 rounded-lg flex flex-col items-center justify-center">
                    <div className="text-2xl mb-1">📍</div>
                    <div className="text-sm">GPS</div>
                  </button>
                  <button className="bg-purple-500 text-white py-3 px-4 rounded-lg flex flex-col items-center justify-center">
                    <div className="text-2xl mb-1">🔢</div>
                    <div className="text-sm">Mã điểm danh</div>
                  </button>
                </div>
              </div>
            </div>
            
            <div className="bg-gray-100 rounded-lg p-4">
              <h3 className="font-semibold text-gray-700 mb-2">Hướng dẫn:</h3>
              <ul className="text-sm text-gray-600 list-disc pl-5 space-y-1">
                <li>Chọn phương thức điểm danh phù hợp</li>
                <li>Điểm danh chỉ có hiệu lực khi bạn ở trong phạm vi lớp học</li>
                <li>Nếu gặp sự cố, hãy liên hệ giảng viên</li>
              </ul>
            </div>
          </div>
        );
        
      case 'history':
        return (
          <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Lịch sử điểm danh</h2>
            
            <div className="space-y-4">
              {coursesWithSessions.map((course) => (
                <div key={course.id} className="bg-white rounded-lg shadow overflow-hidden">
                  <div 
                    className="bg-gray-50 p-4 flex justify-between items-center cursor-pointer hover:bg-gray-100"
                    onClick={() => setExpandedCourse(expandedCourse === course.id ? null : course.id)}
                  >
                    <div>
                      <h3 className="font-semibold text-lg">{course.name}</h3>
                      <p className="text-sm text-gray-600">Mã môn: {course.id}</p>
                    </div>
                    <div className="flex items-center">
                      <div className="text-sm mr-3">
                        <span className="text-green-600 font-medium">
                          {course.sessions.filter(s => s.status === 'present').length}
                        </span>
                        /
                        <span className="font-medium">{course.sessions.length}</span>
                        &nbsp;buổi
                      </div>
                      <ChevronDown
                        className={`w-5 h-5 transition-transform ${expandedCourse === course.id ? 'transform rotate-180' : ''}`}
                      />
                    </div>
                  </div>
                  
                  {expandedCourse === course.id && (
                    <div className="overflow-x-auto">
                      <div className="block md:hidden">
                        {/* Mobile view - cards instead of table */}
                        <div className="divide-y divide-gray-200">
                          {course.sessions.map((session) => (
                            <div key={session.id} className="p-4">
                              <div className="flex justify-between mb-2">
                                <div className="font-medium">{session.name}</div>
                                <span className={`${getStatusColor(session.status)} font-medium`}>
                                  {getStatusText(session.status)}
                                </span>
                              </div>
                              <div className="text-sm text-gray-600">
                                <div>Ngày: {session.date}</div>
                                <div>Giờ ĐD: {session.time || '—'}</div>
                                <div>Phương thức: {session.method || '—'}</div>
                              </div>
                            </div>
                          ))}
                        </div>
                      </div>

                      {/* Desktop view - table */}
                      <table className="hidden md:table min-w-full">
                        <thead>
                          <tr className="bg-gray-100">
                            <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Buổi học</th>
                            <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Ngày</th>
                            <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Giờ ĐD</th>
                            <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Trạng thái</th>
                            <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Phương thức</th>
                          </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-200">
                          {course.sessions.map((session) => (
                            <tr key={session.id}>
                              <td className="py-3 px-4 text-sm font-medium">{session.name}</td>
                              <td className="py-3 px-4 text-sm">{session.date}</td>
                              <td className="py-3 px-4 text-sm">{session.time || '—'}</td>
                              <td className="py-3 px-4 text-sm">
                                <span className={`${getStatusColor(session.status)} font-medium`}>
                                  {getStatusText(session.status)}
                                </span>
                              </td>
                              <td className="py-3 px-4 text-sm">{session.method || '—'}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  )}
                </div>
              ))}
            </div>
            
            <div className="mt-6 bg-gray-50 rounded-lg p-4">
              <h3 className="font-semibold text-gray-700 mb-2">Chú thích:</h3>
              <div className="grid grid-cols-1 sm:grid-cols-3 gap-2 text-sm">
                <div className="flex items-center">
                  <span className="inline-block w-3 h-3 bg-green-600 rounded-full mr-2"></span>
                  <span>Có mặt</span>
                </div>
                <div className="flex items-center">
                  <span className="inline-block w-3 h-3 bg-red-600 rounded-full mr-2"></span>
                  <span>Vắng mặt</span>
                </div>
                <div className="flex items-center">
                  <span className="inline-block w-3 h-3 bg-yellow-600 rounded-full mr-2"></span>
                  <span>Có phép</span>
                </div>
              </div>
            </div>
          </div>
        );
        
      case 'statistics':
        return (
          <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Thống kê điểm danh</h2>
            
            <div className="space-y-6">
              {courses.map((course) => (
                <div key={course.id} className="bg-white rounded-lg shadow p-4">
                  <h3 className="font-semibold text-lg mb-2">{course.name}</h3>
                  <div className="text-sm text-gray-600 mb-3">Mã môn: {course.id} | GV: {course.teacher}</div>
                  
                  <div className="mb-2 flex justify-between items-center">
                    <span className="text-sm font-medium">Tỷ lệ điểm danh: {course.attendance}%</span>
                    <span className="text-sm text-gray-600">{course.attended}/{course.total} buổi</span>
                  </div>
                  
                  <div className="w-full bg-gray-200 rounded-full h-2.5">
                    <div 
                      className="bg-blue-600 h-2.5 rounded-full" 
                      style={{ width: `${course.attendance}%` }}
                    ></div>
                  </div>
                  
                  <div className="flex mt-4 text-sm">
                    <div className="flex-1 text-center">
                      <div className="font-bold text-green-600">{course.attended}</div>
                      <div className="text-gray-600">Có mặt</div>
                    </div>
                    <div className="flex-1 text-center">
                      <div className="font-bold text-red-600">{course.absent}</div>
                      <div className="text-gray-600">Vắng mặt</div>
                    </div>
                    <div className="flex-1 text-center">
                      <div className="font-bold text-yellow-600">{course.excused}</div>
                      <div className="text-gray-600">Có phép</div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        );
        
      case 'absence':
        return (
          <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Đơn xin phép vắng học</h2>
            
            <div className="bg-white rounded-lg shadow p-4">
              <form>
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Môn học:</label>
                  <select className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <option value="">Chọn môn học</option>
                    {courses.map(course => (
                      <option key={course.id} value={course.id}>{course.name}</option>
                    ))}
                  </select>
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ngày vắng:</label>
                  <input 
                    type="date" 
                    className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  />
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Lý do vắng:</label>
                  <textarea 
                    className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 min-h-24"
                    placeholder="Nhập lý do vắng học..."
                  ></textarea>
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-1">Tập tin đính kèm:</label>
                  <div className="border border-dashed border-gray-300 rounded-md p-4 text-center">
                    <p className="text-sm text-gray-500 mb-2">Kéo thả hoặc nhấp vào đây để tải lên tập tin</p>
                    <button type="button" className="text-sm text-blue-500 font-medium">Chọn tập tin</button>
                  </div>
                </div>
                
                <button
                  type="submit"
                  className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 font-medium"
                >
                  Gửi đơn xin phép
                </button>
              </form>
            </div>
            
            <div className="mt-6">
              <h3 className="font-semibold mb-3">Đơn xin phép đã gửi</h3>
              
              {/* Mobile cards */}
              <div className="block md:hidden space-y-3">
                <div className="bg-white rounded-lg shadow p-4">
                  <div className="font-medium mb-1">Lập trình nâng cao</div>
                  <div className="text-sm text-gray-600 mb-2">Ngày gửi: 27/04/2025</div>
                  <span className="text-green-600 font-medium text-sm">Đã duyệt</span>
                </div>
                
                <div className="bg-white rounded-lg shadow p-4">
                  <div className="font-medium mb-1">Cơ sở dữ liệu</div>
                  <div className="text-sm text-gray-600 mb-2">Ngày gửi: 15/04/2025</div>
                  <span className="text-red-600 font-medium text-sm">Từ chối</span>
                </div>
                
                <div className="bg-white rounded-lg shadow p-4">
                  <div className="font-medium mb-1">Hệ thống thông tin</div>
                  <div className="text-sm text-gray-600 mb-2">Ngày gửi: 02/05/2025</div>
                  <span className="text-yellow-600 font-medium text-sm">Đang xét duyệt</span>
                </div>
              </div>
              
              {/* Desktop table */}
              <div className="hidden md:block bg-white rounded-lg shadow overflow-hidden">
                <table className="min-w-full">
                  <thead>
                    <tr className="bg-gray-100">
                      <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Ngày gửi</th>
                      <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Môn học</th>
                      <th className="py-3 px-4 text-left text-sm font-medium text-gray-600">Trạng thái</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200">
                    <tr>
                      <td className="py-3 px-4 text-sm">27/04/2025</td>
                      <td className="py-3 px-4 text-sm">Lập trình nâng cao</td>
                      <td className="py-3 px-4 text-sm">
                        <span className="text-green-600 font-medium">Đã duyệt</span>
                      </td>
                    </tr>
                    <tr>
                      <td className="py-3 px-4 text-sm">15/04/2025</td>
                      <td className="py-3 px-4 text-sm">Cơ sở dữ liệu</td>
                      <td className="py-3 px-4 text-sm">
                        <span className="text-red-600 font-medium">Từ chối</span>
                      </td>
                    </tr>
                    <tr>
                      <td className="py-3 px-4 text-sm">02/05/2025</td>
                      <td className="py-3 px-4 text-sm">Hệ thống thông tin</td>
                      <td className="py-3 px-4 text-sm">
                        <span className="text-yellow-600 font-medium">Đang xét duyệt</span>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        );
        
      case 'notifications': {
        const markAsRead = (id) => {
          setNotifications(prev => 
            prev.map(notification => 
              notification.id === id ? {...notification, isRead: true} : notification
            )
          );
        };

        const markAllAsRead = () => {
          setNotifications(prev => 
            prev.map(notification => ({...notification, isRead: true}))
          );
        };
        
        return (
          <div className="p-4">
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-xl font-bold">Thông báo</h2>
              {notifications.some(n => !n.isRead) && (
                <button 
                  onClick={markAllAsRead}
                  className="text-sm text-blue-600 font-medium"
                >
                  Đánh dấu tất cả đã đọc
                </button>
              )}
            </div>
            
            <div className="bg-white rounded-lg shadow divide-y divide-gray-200">
              {notifications.map((notification) => (
                <div 
                  key={notification.id} 
                  className={`p-4 ${notification.isRead ? 'bg-white' : 'bg-blue-50'}`}
                  onClick={() => markAsRead(notification.id)}
                >
                  <div className="flex justify-between items-start">
                    <div>
                      <h3 className={`font-medium ${notification.isRead ? 'text-gray-900' : 'text-blue-700'}`}>
                        {notification.title}
                      </h3>
                      <p className="text-sm text-gray-600 mt-1">{notification.content}</p>
                    </div>
                    <span className="text-xs text-gray-500 ml-2">{notification.time}</span>
                  </div>
                </div>
              ))}
              
              {notifications.length === 0 && (
                <div className="p-6 text-center text-gray-500">
                  Không có thông báo nào
                </div>
              )}
            </div>
          </div>
        );
      }
        
      case 'messages':
        return (
          <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Thông báo từ giảng viên</h2>
            
            <div className="bg-white rounded-lg shadow divide-y divide-gray-200">
              <div className="p-4">
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="font-medium">Thông báo từ GV Nguyễn Văn B</h3>
                    <p className="text-sm text-gray-500 mt-1">Lập trình nâng cao</p>
                    <p className="text-sm mt-2">Các em lưu ý chuẩn bị bài tập để nộp vào buổi học tới nhé.</p>
                  </div>
                  <span className="text-xs text-gray-500">9:30 AM, 03/05/2025</span>
                </div>
              </div>
              
              <div className="p-4">
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="font-medium">Thông báo từ GV Trần Thị C</h3>
                    <p className="text-sm text-gray-500 mt-1">Hệ thống thông tin</p>
                    <p className="text-sm mt-2">Buổi học ngày mai sẽ có kiểm tra 15 phút đầu giờ, các em đến đúng giờ nhé.</p>
                  </div>
                  <span className="text-xs text-gray-500">2:45 PM, 02/05/2025</span>
                </div>
              </div>
              
              <div className="p-4">
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="font-medium">Thông báo từ GV Lê Văn D</h3>
                    <p className="text-sm text-gray-500 mt-1">Cơ sở dữ liệu</p>
                    <p className="text-sm mt-2">Các em nộp bài tập lớn trước ngày 10/05 nhé.</p>
                  </div>
                  <span className="text-xs text-gray-500">11:15 AM, 01/05/2025</span>
                </div>
              </div>
            </div>
          </div>
        );
        
      case 'profile':
        return (
          <div className="p-4">
            <h2 className="text-xl font-bold mb-4">Thông tin cá nhân</h2>
            
            <div className="bg-white rounded-lg shadow overflow-hidden">
              <div className="p-4 sm:p-6 flex flex-col sm:flex-row items-center sm:items-start">
                <div className="w-24 h-24 mb-4 sm:mb-0 sm:mr-6">
                  <img 
                    src={studentInfo.avatar} 
                    alt="Avatar"
                    className="w-full h-full rounded-full object-cover"
                  />
                </div>
                
                <div className="text-center sm:text-left">
                  <h3 className="text-lg font-semibold">{studentInfo.name}</h3>
                  <p className="text-gray-600 mt-1">MSSV: {studentInfo.id}</p>
                  <p className="text-gray-600">Lớp: {studentInfo.class}</p>
                </div>
              </div>
              
              <div className="border-t border-gray-200 px-4 py-5 sm:p-6">
                <h3 className="text-lg font-medium mb-4">Thông tin liên hệ</h3>
                
                <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Email:</label>
                    <input
                      type="email"
                      className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      value={studentInfo.email}
                      readOnly
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Số điện thoại:</label>
                    <input
                      type="tel"
                      className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                      value={studentInfo.phone}
                      readOnly
                    />
                  </div>
                </div>
                
                <div className="mt-4">
                  <button className="text-blue-600 font-medium text-sm">
                    Yêu cầu cập nhật thông tin
                  </button>
                </div>
              </div>
              
              <div className="border-t border-gray-200 px-4 py-5 sm:p-6">
                <h3 className="text-lg font-medium mb-4">Bảo mật</h3>
                <button className="bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 font-medium">
                  Đổi mật khẩu
                </button>
              </div>
            </div>
          </div>
        );
        
      default:
        return null;
    }
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      {/* Desktop Sidebar */}
      <div className="hidden lg:flex lg:flex-col lg:w-64 bg-blue-700 text-white">
        <div className="p-4 flex items-center">
          <span className="font-bold text-xl">Attendance SYS</span>
        </div>
        
        <div className="flex-1 overflow-y-auto">
          <nav className="mt-5 px-2">
            {navItems.map((item) => (
              <div
                key={item.id}
                className={`flex items-center px-4 py-3 mb-1 rounded-lg cursor-pointer ${
                  activeTab === item.id 
                    ? 'bg-blue-800 text-white' 
                    : 'text-blue-100 hover:bg-blue-800 hover:text-white'
                }`}
                onClick={() => setActiveTab(item.id)}
              >
                <span className="mr-3">{item.icon}</span>
                <span>{item.title}</span>
                {item.badge && (
                  <span className="ml-auto bg-red-500 text-white text-xs px-2 py-0.5 rounded-full">
                    {item.badge}
                  </span>
                )}
              </div>
            ))}
          </nav>
        </div>
        
        <div className="p-4 border-t border-blue-800">
          <div className="flex items-center px-4 py-3 text-blue-100 hover:bg-blue-800 hover:text-white rounded-lg cursor-pointer">
            <LogOut size={20} className="mr-3" />
            <span>Đăng xuất</span>
          </div>
        </div>
      </div>
      
      {/* Mobile Header and Content */}
      <div className="flex-1 flex flex-col">
        {/* Mobile Header */}
        <div className="lg:hidden bg-blue-700 text-white p-4 flex justify-between items-center">
          <span className="font-bold text-xl">Attendance SYS</span>
          <button 
            className="rounded-md p-1 text-blue-100 hover:bg-blue-800 hover:text-white"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          >
            {mobileMenuOpen ? <X size={24} /> : <Menu size={24} />}
          </button>
        </div>
        
        {/* Mobile Menu */}
        {mobileMenuOpen && (
          <div className="lg:hidden bg-blue-700 text-white">
            <nav className="px-2 py-3">
              {navItems.map((item) => (
                <div
                  key={item.id}
                  className={`flex items-center px-4 py-3 mb-1 rounded-lg cursor-pointer ${
                    activeTab === item.id 
                      ? 'bg-blue-800 text-white' 
                      : 'text-blue-100 hover:bg-blue-800 hover:text-white'
                  }`}
                  onClick={() => setActiveTab(item.id)}
                >
                  <span className="mr-3">{item.icon}</span>
                  <span>{item.title}</span>
                  {item.badge && (
                    <span className="ml-auto bg-red-500 text-white text-xs px-2 py-0.5 rounded-full">
                      {item.badge}
                    </span>
                  )}
                </div>
              ))}
              
              <div className="flex items-center px-4 py-3 mt-2 border-t border-blue-800 text-blue-100 hover:bg-blue-800 hover:text-white rounded-lg cursor-pointer">
                <LogOut size={20} className="mr-3" />
                <span>Đăng xuất</span>
              </div>
            </nav>
          </div>
        )}
        
        {/* Main Content */}
        <main className="flex-1 overflow-y-auto">
          {renderContent()}
        </main>
      </div>
    </div>
  );
};

export default StudentAttendanceApp;