import React, { useState, ReactNode } from 'react';

interface Tab {
  id: string;
  label: string;
}

interface TabsProps {
  tabs: Tab[];
  children: ReactNode;
}

export const Tabs: React.FC<TabsProps> = ({ tabs, children }) => {
  const [activeTab, setActiveTab] = useState(tabs[0].id);
  
  // Find all direct children that are React elements
  const childrenArray = React.Children.toArray(children);
  
  // Filter for the child that matches the active tab
  const activeChild = childrenArray.find((child) => {
    if (React.isValidElement(child) && child.props.id === activeTab) {
      return true;
    }
    return false;
  });

  return (
    <div>
      <div className="mb-6 border-b border-gray-200">
        <nav className="-mb-px flex space-x-8" aria-label="Tabs">
          {tabs.map((tab) => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`
                whitespace-nowrap py-4 px-1 border-b-2 font-medium text-sm
                ${activeTab === tab.id
                  ? 'border-blue-500 text-blue-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'}
                transition-colors duration-200
              `}
              aria-current={activeTab === tab.id ? 'page' : undefined}
            >
              {tab.label}
            </button>
          ))}
        </nav>
      </div>
      
      <div className="py-4">
        {activeChild}
      </div>
    </div>
  );
};