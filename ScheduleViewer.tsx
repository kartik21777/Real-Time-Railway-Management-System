import React, { useState, useMemo } from 'react';
import { Train } from '../types/Train';
import { Platform } from '../types/Platform';
import { Allocation } from '../types/Allocation';
import { Search, ArrowUpDown } from 'lucide-react';

interface ScheduleViewerProps {
  trains: Train[];
  platforms: Platform[];
  allocations: Allocation[];
}

type SortField = 'name' | 'departureTime' | 'arrivalTime' | 'platform';
type SortDirection = 'asc' | 'desc';

interface SortState {
  field: SortField;
  direction: SortDirection;
}

const ScheduleViewer: React.FC<ScheduleViewerProps> = ({ 
  trains, 
  platforms, 
  allocations 
}) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [sortState, setSortState] = useState<SortState>({
    field: 'departureTime',
    direction: 'asc'
  });
  
  // Get platform number for a train
  const getPlatformForTrain = (trainId: string) => {
    const allocation = allocations.find(a => a.trainId === trainId);
    if (!allocation) return 'Not Assigned';
    
    const platform = platforms.find(p => p.id === allocation.platformId);
    return platform ? `Platform ${platform.number}` : 'Unknown';
  };
  
  // Toggle sort direction or change sort field
  const handleSort = (field: SortField) => {
    setSortState(prev => {
      if (prev.field === field) {
        return {
          field,
          direction: prev.direction === 'asc' ? 'desc' : 'asc'
        };
      }
      return {
        field,
        direction: 'asc'
      };
    });
  };
  
  // Get sort indicator
  const getSortIndicator = (field: SortField) => {
    if (sortState.field !== field) return null;
    
    return (
      <span className="ml-1 inline-block">
        <ArrowUpDown size={16} className="text-gray-500" />
      </span>
    );
  };
  
  // Filter and sort trains
  const filteredAndSortedTrains = useMemo(() => {
    // First filter
    const filtered = trains.filter(train => {
      const searchLower = searchTerm.toLowerCase();
      return (
        train.name.toLowerCase().includes(searchLower) ||
        train.number.toLowerCase().includes(searchLower) ||
        train.origin.toLowerCase().includes(searchLower) ||
        train.destination.toLowerCase().includes(searchLower)
      );
    });
    
    // Then sort
    return [...filtered].sort((a, b) => {
      const { field, direction } = sortState;
      const multiplier = direction === 'asc' ? 1 : -1;
      
      switch (field) {
        case 'name':
          return multiplier * a.name.localeCompare(b.name);
          
        case 'departureTime':
          return multiplier * a.departureTime.localeCompare(b.departureTime);
          
        case 'arrivalTime':
          return multiplier * a.arrivalTime.localeCompare(b.arrivalTime);
          
        case 'platform': {
          const platformA = getPlatformForTrain(a.id);
          const platformB = getPlatformForTrain(b.id);
          return multiplier * platformA.localeCompare(platformB);
        }
          
        default:
          return 0;
      }
    });
  }, [trains, searchTerm, sortState, allocations, platforms]);

  return (
    <div className="space-y-6">
      <section>
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-6">
          <h2 className="text-xl font-semibold text-gray-900">Train Schedule</h2>
          
          <div className="relative w-full md:w-64">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <Search size={18} className="text-gray-400" />
            </div>
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Search trains..."
              className="pl-10 w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm
                       bg-white border px-3 py-2"
            />
          </div>
        </div>
        
        <div className="overflow-x-auto bg-white shadow-sm rounded-lg">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Train Number
                </th>
                <th 
                  scope="col" 
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                  onClick={() => handleSort('name')}
                >
                  <div className="flex items-center">
                    Train Name
                    {getSortIndicator('name')}
                  </div>
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Route
                </th>
                <th 
                  scope="col" 
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                  onClick={() => handleSort('departureTime')}
                >
                  <div className="flex items-center">
                    Departure
                    {getSortIndicator('departureTime')}
                  </div>
                </th>
                <th 
                  scope="col" 
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                  onClick={() => handleSort('arrivalTime')}
                >
                  <div className="flex items-center">
                    Arrival
                    {getSortIndicator('arrivalTime')}
                  </div>
                </th>
                <th 
                  scope="col" 
                  className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                  onClick={() => handleSort('platform')}
                >
                  <div className="flex items-center">
                    Platform
                    {getSortIndicator('platform')}
                  </div>
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {filteredAndSortedTrains.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-6 py-4 text-center text-sm text-gray-500">
                    {searchTerm ? 'No trains match your search.' : 'No trains found.'}
                  </td>
                </tr>
              ) : (
                filteredAndSortedTrains.map((train) => (
                  <tr key={train.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
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
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {getPlatformForTrain(train.id)}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </section>
      
      <section className="bg-blue-50 p-4 rounded-lg border border-blue-100">
        <h3 className="text-sm font-medium text-blue-800 mb-2">Schedule Information</h3>
        <p className="text-sm text-blue-700">
          This schedule shows all trains with their assigned platforms. You can sort by clicking on column headers and search using the search box above.
        </p>
      </section>
    </div>
  );
};

export default ScheduleViewer;