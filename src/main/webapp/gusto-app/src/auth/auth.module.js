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
    },
    logout(state) {
      state.loggedIn = false;
      state.username = null;
      state.role = null;
      state.access_token = null;
      state.id = null;
      localStorage.setItem('token', null);
      localStorage.setItem('loggedIn', false);
      localStorage.setItem('username', null);
      localStorage.setItem('role', null);
      localStorage.setItem('id', null);
    },
    getState() {
      return this.state;
    }
  }
};
