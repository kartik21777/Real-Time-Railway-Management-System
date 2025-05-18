import React, { useState } from 'react';
import { Platform, PlatformStatus } from '../types/Platform';
import { Trash2 } from 'lucide-react';

interface PlatformManagementProps {
  platforms: Platform[];
  setPlatforms: React.Dispatch<React.SetStateAction<Platform[]>>;
}

const PlatformManagement: React.FC<PlatformManagementProps> = ({ platforms, setPlatforms }) => {
  const [formData, setFormData] = useState<Omit<Platform, 'id'>>({
    number: '',
    capacity: 2,
    status: 'Available'
  });
  
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    
    setFormData(prev => ({
      ...prev,
      [name]: name === 'capacity' ? parseInt(value) : value
    }));
  };
  
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Simple validation
    if (!formData.number || formData.capacity <= 0) {
      alert('Please fill in all required fields with valid values');
      return;
    }
    
    // Create new platform with generated id
    const newPlatform: Platform = {
      ...formData,
      id: `P${Math.floor(Math.random() * 10000).toString().padStart(4, '0')}`
    };
    
    setPlatforms(prev => [...prev, newPlatform]);
    
    // Reset form
    setFormData({
      number: '',
      capacity: 2,
      status: 'Available'
    });
  };
  
  const handleDelete = (id: string) => {
    if (confirm('Are you sure you want to delete this platform?')) {
      setPlatforms(prev => prev.filter(platform => platform.id !== id));
    }
  };
  
  const getStatusColor = (status: PlatformStatus) => {
    switch (status) {
      case 'Available':
        return 'bg-green-100 text-green-800';
      case 'Occupied':
        return 'bg-blue-100 text-blue-800';
      case 'Maintenance':
        return 'bg-amber-100 text-amber-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="space-y-8">
      <section>
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Add New Platform</h2>
        
        <form onSubmit={handleSubmit} className="bg-white shadow-sm rounded-lg p-6 grid grid-cols-1 gap-6 md:grid-cols-3">
          <div>
            <label htmlFor="number" className="block text-sm font-medium text-gray-700 mb-1">
              Platform Number*
            </label>
            <input
              type="text"
              id="number"
              name="number"
              value={formData.number}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            />
          </div>
          
          <div>
            <label htmlFor="capacity" className="block text-sm font-medium text-gray-700 mb-1">
              Capacity*
            </label>
            <input
              type="number"
              id="capacity"
              name="capacity"
              min="1"
              value={formData.capacity}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            />
          </div>
          
          <div>
            <label htmlFor="status" className="block text-sm font-medium text-gray-700 mb-1">
              Status
            </label>
            <select
              id="status"
              name="status"
              value={formData.status}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
            >
              <option value="Available">Available</option>
              <option value="Occupied">Occupied</option>
              <option value="Maintenance">Maintenance</option>
            </select>
          </div>
          
          <div className="md:col-span-3">
            <button
              type="submit"
              className="w-full sm:w-auto bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-md
                       transition duration-150 ease-in-out focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Add Platform
            </button>
          </div>
        </form>
      </section>
      
      <section>
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Current Platforms</h2>
        
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          {platforms.length === 0 ? (
            <div className="col-span-full bg-white p-6 rounded-lg shadow-sm text-center text-gray-500">
              No platforms found. Add a platform to get started.
            </div>
          ) : (
            platforms.map(platform => (
              <div key={platform.id} className="bg-white overflow-hidden shadow-sm rounded-lg">
                <div className="px-4 py-5 sm:p-6">
                  <div className="flex items-center justify-between">
                    <h3 className="text-lg font-medium text-gray-900">Platform {platform.number}</h3>
                    <button
                      onClick={() => handleDelete(platform.id)}
                      className="text-red-600 hover:text-red-900 transition duration-150 ease-in-out"
                      aria-label={`Delete Platform ${platform.number}`}
                    >
                      <Trash2 size={18} />
                    </button>
                  </div>
                  
                  <div className="mt-4 space-y-3">
                    <div>
                      <p className="text-sm text-gray-500">ID</p>
                      <p className="mt-1 text-sm font-medium text-gray-900">{platform.id}</p>
                    </div>
                    
                    <div>
                      <p className="text-sm text-gray-500">Capacity</p>
                      <p className="mt-1 text-sm font-medium text-gray-900">{platform.capacity} trains</p>
                    </div>
                    
                    <div>
                      <p className="text-sm text-gray-500">Status</p>
                      <span className={`mt-1 inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(platform.status)}`}>
                        {platform.status}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </section>
    </div>
  );
};

export default PlatformManagement;