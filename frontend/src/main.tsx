import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import {BrowserRouter} from 'react-router-dom';
import './index.css'
import App from './App.tsx'

import {createConnectTransport} from "@connectrpc/connect-web";
import {TransportProvider} from "@connectrpc/connect-query";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";

const finalTransport = createConnectTransport({
    baseUrl: "http://localhost:9090",
});

const queryClient = new QueryClient();

createRoot(document.getElementById('root')!)
    .render(
        <StrictMode>
            <TransportProvider transport={finalTransport}>
                <QueryClientProvider client={queryClient}>
                    <BrowserRouter>
                        <App/>
                    </BrowserRouter>
                </QueryClientProvider>
            </TransportProvider>
        </StrictMode>,
    );
