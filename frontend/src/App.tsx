// import React from 'react';
import {Routes, Route} from 'react-router-dom';
import {GroupListPage} from './page/GroupListPage.tsx';
import {GroupPage} from './page/GroupPage.tsx';
import './App.css';

// Import ModuleRegistry and the required module
import {
    ModuleRegistry,
    AllCommunityModule, // or AllEnterpriseModule
} from 'ag-grid-community';

// Register the module
ModuleRegistry.registerModules([
    AllCommunityModule, // or AllEnterpriseModule
]);

export function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<GroupListPage/>}/>
                <Route path="/group/:groupId" element={<GroupPage/>}/>
            </Routes>
        </div>
    );
}