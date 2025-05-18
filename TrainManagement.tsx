import React, { useState } from 'react';
import { Train } from '../types/Train';
import { TrainIcon, Trash2 } from 'lucide-react';

interface TrainManagementProps {
  trains: Train[];
  setTrains: React.Dispatch<React.SetStateAction<Train[]>>;
}

const TrainManagement: React.FC<TrainManagementProps> = ({ trains, setTrains }) => {
  const [formData, setFormData] = useState<Omit<Train, 'id'>>({
    number: '',
    name: '',
    origin: '',
    destination: '',
    departureTime: '',
    arrivalTime: ''
  });
  
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };
  
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Simple validation
    if (!formData.number || !formData.name || !formData.origin || !formData.destination) {
      alert('Please fill in all required fields');
      return;
    }
    
    // Create new train with generated id
    const newTrain: Train = {
      ...formData,
      id: `T${Math.floor(Math.random() * 10000).toString().padStart(4, '0')}`
    };
    
    setTrains(prev => [...prev, newTrain]);
    
    // Reset form
    setFormData({
      number: '',
      name: '',
      origin: '',
      destination: '',
      departureTime: '',
      arrivalTime: ''
    });
  };
  
  const handleDelete = (id: string) => {
    if (confirm('Are you sure you want to delete this train?')) {
      setTrains(prev => prev.filter(train => train.id !== id));
    }
  };

  return (
    <div className="space-y-8">
      <section>
        <h2 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
          <TrainIcon className="mr-2 text-blue-600" size={24} />
          Add New Train
        </h2>
        
        <form onSubmit={handleSubmit} className="bg-white shadow-sm rounded-lg p-6 grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
          <div>
            <label htmlFor="number" className="block text-sm font-medium text-gray-700 mb-1">
              Train Number*
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
            <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-1">
              Train Name*
            </label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            />
          </div>
          
          <div>
            <label htmlFor="origin" className="block text-sm font-medium text-gray-700 mb-1">
              Origin*
            </label>
            <input
              type="text"
              id="origin"
              name="origin"
              value={formData.origin}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            />
          </div>
          
          <div>
            <label htmlFor="destination" className="block text-sm font-medium text-gray-700 mb-1">
              Destination*
            </label>
            <input
              type="text"
              id="destination"
              name="destination"
              value={formData.destination}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            />
          </div>
          
          <div>
            <label htmlFor="departureTime" className="block text-sm font-medium text-gray-700 mb-1">
              Departure Time*
            </label>
            <input
              type="time"
              id="departureTime"
              name="departureTime"
              value={formData.departureTime}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            />
          </div>
          
          <div>
            <label htmlFor="arrivalTime" className="block text-sm font-medium text-gray-700 mb-1">
              Arrival Time*
            </label>
            <input
              type="time"
              id="arrivalTime"
              name="arrivalTime"
              value={formData.arrivalTime}
              onChange={handleChange}
              className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
              required
            />
          </div>
          
          <div className="md:col-span-2 lg:col-span-3">
            <button
              type="submit"
              className="w-full sm:w-auto bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-md
                       transition duration-150 ease-in-out focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Add Train
            </button>
          </div>
        </form>
      </section>
      
      <section>
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Current Trains</h2>
        
        <div className="overflow-x-auto bg-white shadow-sm rounded-lg">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Train ID
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Number
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Name
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Route
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Departure
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Arrival
                </th>
                <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {trains.length === 0 ? (
                <tr>
                  <td colSpan={7} className="px-6 py-4 text-center text-sm text-gray-500">
                    No trains found. Add a train to get started.
                  </td>
                </tr>
              ) : (
                trains.map((train) => (
                  <tr key={train.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      {train.id}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {train.number}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {train.name}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {train.origin} → {train.destination}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {train.departureTime}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {train.arrivalTime}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                      <button
                        onClick={() => handleDelete(train.id)}
                        className="text-red-600 hover:text-red-900 transition duration-150 ease-in-out"
                        aria-label={`Delete ${train.name}`}
                      >
                        <Trash2 size={18} />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </section>
    </div>
  );
};

export default TrainManagement;