export const auth = {
  namespaced: true,

  state: {
    access_token: localStorage.getItem('token') != null ? localStorage.getItem('token') :  null,
    loggedIn: localStorage.getItem('loggedIn') != null ? localStorage.getItem('loggedIn') :  false,
    username: localStorage.getItem('username') != null ? localStorage.getItem('username') :  null,
    role: localStorage.getItem('role') != null ? localStorage.getItem('role') :  null,
  },
  mutations: {
    setJWT(state, token) {
      localStorage.setItem('token', token)
      state.access_token = token
    },
    logout(state) {
      state.loggedIn = false;
      state.username = '';
      state.role = '';
      state.access_token = null
      localStorage.setItem('token', null);
    }
  }
};
