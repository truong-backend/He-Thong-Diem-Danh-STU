import React, { useState } from 'react';
import LoginForm from './components/features/LoginForm';;
import StudentAttendanceApp from './components/features/StudentAttendanceApp';
import AdminDashboard from './components/features/AdminDashboard';
import LecturerDashboard from './components/features/LecturerDashboard';

const App = () => {
  const [activeComponent, setActiveComponent] = useState('loginForm');

  const renderComponent = () => {
    switch (activeComponent) {
      case 'loginForm': return <LoginForm />;
      case 'studentAttendanceApp': return <StudentAttendanceApp />;
      case 'adminDashboard': return <AdminDashboard />;
      case 'lecturerDashboard': return <LecturerDashboard />;
       //default: return <StudentAttendanceApp />;
    }
  };

  return (
    <div className="min-h-screen p-4 bg-gray-100">
      <div className="flex flex-wrap gap-2 mb-6 justify-center">
        <button onClick={() => setActiveComponent('loginForm')} className="px-4 py-2 bg-blue-500 text-white rounded-lg">LoginForm</button>
        <button onClick={() => setActiveComponent('studentAttendanceApp')} className="px-4 py-2 bg-blue-500 text-white rounded-lg">StudentAttendanceApp</button>
        <button onClick={() => setActiveComponent('adminDashboard')} className="px-4 py-2 bg-blue-500 text-white rounded-lg">AdminDashboard</button>
        <button onClick={() => setActiveComponent('lecturerDashboard')} className="px-4 py-2 bg-blue-500 text-white rounded-lg">LecturerDashboard</button>
      </div>
      

      <div className="bg-white p-6 rounded shadow-md">
        {renderComponent()}
      </div>
    </div>
  );
};

export default App;
