import React, { useState } from 'react';
import { Allocation } from '../types/Allocation';
import { Train } from '../types/Train';
import { Platform } from '../types/Platform';
import { Trash2, Clock } from 'lucide-react';

interface AllocationViewerProps {
  allocations: Allocation[];
  setAllocations: React.Dispatch<React.SetStateAction<Allocation[]>>;
  trains: Train[];
  platforms: Platform[];
}

const AllocationViewer: React.FC<AllocationViewerProps> = ({ 
  allocations, 
  setAllocations, 
  trains, 
  platforms 
}) => {
  const [formData, setFormData] = useState<Omit<Allocation, 'id' | 'timestamp'>>({
    trainId: '',
    platformId: ''
  });
  
  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };
  
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Simple validation
    if (!formData.trainId || !formData.platformId) {
      alert('Please select both a train and a platform');
      return;
    }
    
    // Create new allocation with generated id and current timestamp
    const newAllocation: Allocation = {
      ...formData,
      id: `A${Math.floor(Math.random() * 10000).toString().padStart(4, '0')}`,
      timestamp: new Date().toISOString()
    };
    
    setAllocations(prev => [...prev, newAllocation]);
    
    // Reset form
    setFormData({
      trainId: '',
      platformId: ''
    });
  };
  
  const handleDelete = (id: string) => {
    if (confirm('Are you sure you want to delete this allocation?')) {
      setAllocations(prev => prev.filter(allocation => allocation.id !== id));
    }
  };
  
  // Helper function to find train by ID
  const getTrainById = (id: string) => {
    return trains.find(train => train.id === id);
  };
  
  // Helper function to find platform by ID
  const getPlatformById = (id: string) => {
    return platforms.find(platform => platform.id === id);
  };
  
  // Format date for display
  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    }).format(date);
  };

  return (
    <div className="space-y-8">
      <section>
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Create New Allocation</h2>
        
        <form onSubmit={handleSubmit} className="bg-white shadow-sm rounded-lg p-6 grid grid-cols-1 gap-6 md:grid-cols-2">
          <div>
            <label htmlFor="trainId" className="block text-sm font-medium text-gray-700 mb-1">
              Select Train*
            </label>
            <select
              id="trainId"
              name="trainId"
              value={formData.trainId}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            >
              <option value="">Select a train</option>
              {trains.map(train => (
                <option key={train.id} value={train.id}>
                  {train.number} - {train.name}
                </option>
              ))}
            </select>
          </div>
          
          <div>
            <label htmlFor="platformId" className="block text-sm font-medium text-gray-700 mb-1">
              Select Platform*
            </label>
            <select
              id="platformId"
              name="platformId"
              value={formData.platformId}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            >
              <option value="">Select a platform</option>
              {platforms.map(platform => (
                <option key={platform.id} value={platform.id}>
                  Platform {platform.number} - {platform.status}
                </option>
              ))}
            </select>
          </div>
          
          <div className="md:col-span-2">
            <button
              type="submit"
              className="w-full sm:w-auto bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-md
                       transition duration-150 ease-in-out focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Create Allocation
            </button>
          </div>
        </form>
      </section>
      
      <section>
        <h2 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
          <Clock className="mr-2 text-blue-600" size={24} />
          Recent Allocations
        </h2>
        
        <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
          {allocations.length === 0 ? (
            <div className="col-span-full bg-white p-6 rounded-lg shadow-sm text-center text-gray-500">
              No allocations found. Create an allocation to get started.
            </div>
          ) : (
            allocations
              .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())
              .map(allocation => {
                const train = getTrainById(allocation.trainId);
                const platform = getPlatformById(allocation.platformId);
                
                return (
                  <div key={allocation.id} className="bg-white overflow-hidden shadow-sm rounded-lg border-l-4 border-blue-500 hover:shadow-md transition duration-150 ease-in-out">
                    <div className="px-4 py-5 sm:p-6">
                      <div className="flex items-center justify-between">
                        <h3 className="text-lg font-medium text-gray-900">Allocation #{allocation.id.slice(-4)}</h3>
                        <button
                          onClick={() => handleDelete(allocation.id)}
                          className="text-red-600 hover:text-red-900 transition duration-150 ease-in-out"
                          aria-label={`Delete Allocation ${allocation.id}`}
                        >
                          <Trash2 size={18} />
                        </button>
                      </div>
                      
                      <div className="mt-4 space-y-3">
                        <div>
                          <p className="text-sm text-gray-500">Train</p>
                          <p className="mt-1 text-sm font-medium text-gray-900">
                            {train ? `${train.number} - ${train.name}` : 'Unknown train'}
                          </p>
                        </div>
                        
                        <div>
                          <p className="text-sm text-gray-500">Platform</p>
                          <p className="mt-1 text-sm font-medium text-gray-900">
                            {platform ? `Platform ${platform.number}` : 'Unknown platform'}
                          </p>
                        </div>
                        
                        <div>
                          <p className="text-sm text-gray-500">Allocation Time</p>
                          <p className="mt-1 text-sm font-medium text-gray-900">
                            {formatDate(allocation.timestamp)}
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                );
              })
          )}
        </div>
      </section>
    </div>
  );
};

export default AllocationViewer;