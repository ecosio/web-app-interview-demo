import React, { useState } from "react";
import "./App.css";
import ApiContext, { ApiContextState } from "./ApiContext";
import ContactPanel from "./contacts/ContactPanel";

function createApiContext() {
  return new ApiContextState("http://localhost:8080", "john", "p@ssword1");
}

function App() {
  const [apiContext] = useState(createApiContext());

  return (
    <div className="App">
      <ApiContext.Provider value={apiContext}>
        <ContactPanel />
      </ApiContext.Provider>
    </div>
  );
}

export default App;
