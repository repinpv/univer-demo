// import React from 'react';
import {Routes, Route} from 'react-router-dom';
import {GroupListPage} from './page/GroupListPage.tsx';
import {GroupPage} from './page/GroupPage.tsx';
import './App.css';

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<GroupListPage/>}/>
                <Route path="/group/:groupId" element={<GroupPage/>}/>
            </Routes>
        </div>
    );
}

export default App
