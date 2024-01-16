export const auth = {
  namespaced: true,

  state: {
    access_token: localStorage.getItem('token') != null ? localStorage.getItem('token') :  null,
    loggedIn: false
  },
  mutations: {
    setJWT(state, token) {
      localStorage.setItem('token', token)
      state.access_token = token
    },
    logout(state) {
      state.loggedIn = false
      localStorage.setItem('token', null)
    }
  }
};
