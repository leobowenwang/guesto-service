export const auth = {
  namespaced: true,

  state: {
    access_token: localStorage.getItem('token') != null ? localStorage.getItem('token') :  null,
    loggedIn: false,
    username: localStorage.getItem('username') != null ? localStorage.getItem('username') :  null,
    role: localStorage.getItem('role') != null ? localStorage.getItem('role') :  null,
    id: localStorage.getItem('id') != null ? localStorage.getItem('id') :  null,
  },
  mutations: {
    setJWT(state, token) {
      localStorage.setItem('token', token);
      state.access_token = token;

      localStorage.setItem('authState', JSON.stringify(state));

    },
    logout(state) {
      state.loggedIn = false;
      state.username = null;
      state.role = null;
      state.access_token = null;
      state.id = null;
      localStorage.removeItem('token');
      localStorage.removeItem('authState');
    },
    getState(state) {
      const storedState = JSON.parse(localStorage.getItem('authState'));
      if (storedState) {
        Object.assign(state, storedState);
      }
    }
  }
};
