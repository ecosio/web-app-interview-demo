import React from "react";
import { Configuration, ContactControllerApi } from "./apiclient";

export class ApiContextState {
  public readonly contactApi: ContactControllerApi;

  public constructor(
    url: string,
    user: string | undefined,
    password: string | undefined
  ) {
    const config = new Configuration({
      basePath: url,
      username: user,
      password: password,
    });

    this.contactApi = new ContactControllerApi(config);
  }
}

export const ApiContext = React.createContext<ApiContextState>(
  new ApiContextState("", "", "")
);
export default ApiContext;
