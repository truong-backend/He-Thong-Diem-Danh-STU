import { useState } from 'react';
import { Mail, Lock, Eye, EyeOff, LogIn, AlertCircle, CheckCircle2 } from 'lucide-react';

export default function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(true);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const validateEmail = (email) => {
    return email.toLowerCase().endsWith('.edu.vn') && email.includes('@');
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError('');
    
    // Validate email domain
    if (!validateEmail(email)) {
      setError('Vui lòng sử dụng email với tên miền .edu.vn');
      return;
    }
    
    // Validate password
    if (password.length < 6) {
      setError('Mật khẩu phải có ít nhất 6 ký tự');
      return;
    }
    
    // Simulate login process
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
      alert('Đăng nhập thành công!');
    }, 1500);
  };

  return (
    <div className="flex min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      {/* Left side - Logo and introduction */}
      <div className="hidden lg:flex lg:w-1/2 bg-gradient-to-br from-blue-600 to-indigo-700 flex-col justify-center items-center p-12 text-white relative overflow-hidden">
        {/* Background decoration */}
        <div className="absolute inset-0 opacity-10">
          <div className="absolute top-0 left-0 w-64 h-64 bg-white rounded-full -translate-x-1/2 -translate-y-1/2"></div>
          <div className="absolute bottom-0 right-0 w-96 h-96 bg-white rounded-full translate-x-1/3 translate-y-1/3"></div>
        </div>
        
        <div className="relative z-10 flex flex-col items-center">
          <div className="mb-10 bg-white p-5 rounded-full shadow-lg">
            <img 
              src="logo_truong.png" 
              alt="Logo Đại học Công nghệ Sài Gòn" 
              className="w-24 h-24 mx-auto rounded-full" 
            />
          </div>
          <h1 className="text-3xl font-bold mb-6 text-center">Hệ thống Điểm danh Sinh viên</h1>
          <h2 className="text-2xl font-semibold mb-8 text-center">Đại học Công nghệ Sài Gòn</h2>
          <div className="bg-white/10 backdrop-blur-sm p-6 rounded-xl shadow-lg max-w-md border border-white/20">
            <p className="text-lg text-center">
              Hệ thống quản lý điểm danh hiện đại, giúp theo dõi và báo cáo tình trạng đi học của sinh viên một cách chính xác và hiệu quả.
            </p>
          </div>
          
          <div className="mt-12 flex justify-center space-x-4">
            <div className="flex flex-col items-center">
              <div className="bg-white/20 p-3 rounded-full">
                <CheckCircle2 size={24} className="text-white" />
              </div>
              <p className="mt-2 text-sm text-center">Chính xác</p>
            </div>
            <div className="flex flex-col items-center">
              <div className="bg-white/20 p-3 rounded-full">
                <CheckCircle2 size={24} className="text-white" />
              </div>
              <p className="mt-2 text-sm text-center">Tiện lợi</p>
            </div>
            <div className="flex flex-col items-center">
              <div className="bg-white/20 p-3 rounded-full">
                <CheckCircle2 size={24} className="text-white" />
              </div>
              <p className="mt-2 text-sm text-center">Hiện đại</p>
            </div>
          </div>
        </div>
      </div>
      
      {/* Right side - Login form */}
      <div className="w-full lg:w-1/2 flex items-center justify-center p-8">
        <div className="w-full max-w-md">
          {/* Mobile logo */}
          <div className="block lg:hidden text-center mb-10">
            <div className="inline-flex p-3 rounded-full bg-gradient-to-br from-blue-600 to-indigo-700 shadow-lg mb-4">
              <img 
                src="/api/placeholder/80/80" 
                alt="Logo Đại học Công nghệ Sài Gòn" 
                className="w-16 h-16 mx-auto rounded-full" 
              />
            </div>
            <h1 className="text-2xl font-bold mt-2 bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">Đại học Công nghệ Sài Gòn</h1>
            <h2 className="text-lg text-gray-600">Hệ thống Điểm danh Sinh viên</h2>
          </div>
          
          <div className="bg-white p-8 rounded-2xl shadow-xl border border-gray-100">
            <h2 className="text-2xl font-bold text-center mb-8 bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">Đăng nhập</h2>
            
            {error && (
              <div className="mb-6 p-4 bg-red-50 text-red-700 rounded-xl flex items-start">
                <AlertCircle size={20} className="mr-3 flex-shrink-0 mt-0.5" />
                <p>{error}</p>
              </div>
            )}
            
            <div>
              {/* Email input */}
              <div className="mb-6">
                <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                  Email đại học
                </label>
                <div className="relative group">
                  <div className="absolute inset-y-0 left-0 flex items-center pl-3.5 pointer-events-none">
                    <Mail className="text-gray-400 group-focus-within:text-blue-600 transition-colors" size={18} />
                  </div>
                  <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="pl-10 w-full p-3.5 bg-gray-50 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
                    placeholder="youremail@stu.edu.vn"
                  />
                </div>
                <p className="mt-2 text-xs text-gray-500 flex items-center">
                  <svg className="w-3 h-3 mr-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clipRule="evenodd"></path>
                  </svg>
                  Sử dụng email với tên miền .edu.vn
                </p>
              </div>
              
              {/* Password input */}
              <div className="mb-6">
                <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-2">
                  Mật khẩu
                </label>
                <div className="relative group">
                  <div className="absolute inset-y-0 left-0 flex items-center pl-3.5 pointer-events-none">
                    <Lock className="text-gray-400 group-focus-within:text-blue-600 transition-colors" size={18} />
                  </div>
                  <input
                    id="password"
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="pl-10 w-full p-3.5 bg-gray-50 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all"
                    placeholder="••••••••"
                  />
                  <button 
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-3.5 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                  >
                    {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                  </button>
                </div>
              </div>
              
              {/* Remember me and forgot password */}
              <div className="flex items-center justify-between mb-8">
                <div className="flex items-center">
                  <input
                    id="remember-me"
                    type="checkbox"
                    className="h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                  />
                  <label htmlFor="remember-me" className="ml-2 block text-sm text-gray-700">
                    Ghi nhớ đăng nhập
                  </label>
                </div>
                <div>
                  <a href="#" className="text-sm text-blue-600 hover:text-blue-800 hover:underline transition-colors">
                    Quên mật khẩu?
                  </a>
                </div>
              </div>
              
              {/* Submit button */}
              <button
                onClick={handleSubmit}
                disabled={loading}
                className={`w-full flex justify-center items-center py-3.5 px-4 rounded-xl text-white font-medium shadow-lg ${
                  loading 
                    ? 'bg-blue-400 cursor-not-allowed' 
                    : 'bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700'
                } focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-all`}
              >
                {loading ? (
                  <div className="flex items-center">
                    <svg className="animate-spin -ml-1 mr-2 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                      <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                      <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                    Đang xử lý...
                  </div>
                ) : (
                  <>
                    <LogIn size={20} className="mr-2" />
                    Đăng nhập
                  </>
                )}
              </button>
            </div>
            
            <div className="mt-8 text-center">
              <p className="text-sm text-gray-600">
                Sinh viên mới? <a href="#" className="text-blue-600 hover:text-blue-800 hover:underline font-medium transition-colors">Liên hệ phòng Đào tạo</a>
              </p>
            </div>
          </div>
          
          <div className="mt-8 text-center text-gray-500 text-sm">
            © {new Date().getFullYear()} Đại học Công nghệ Sài Gòn. Bảo lưu mọi quyền.
          </div>
        </div>
      </div>
    </div>
  );
}