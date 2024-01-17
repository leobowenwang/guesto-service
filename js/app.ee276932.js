(function(){"use strict";var e={7512:function(e,t,s){var a=s(9242),l=(s(560),s(3396)),i=s(4870),r=s(7139);const n={namespaced:!0,state:{access_token:null!=localStorage.getItem("token")?localStorage.getItem("token"):null,loggedIn:!1,username:null!=localStorage.getItem("username")?localStorage.getItem("username"):null,role:null!=localStorage.getItem("role")?localStorage.getItem("role"):null,id:null!=localStorage.getItem("id")?localStorage.getItem("id"):null},mutations:{setJWT(e,t){localStorage.setItem("token",t),e.access_token=t,localStorage.setItem("authState",JSON.stringify(e))},logout(e){e.loggedIn=!1,e.username=null,e.role=null,e.access_token=null,e.id=null,localStorage.removeItem("token"),localStorage.removeItem("authState")},getState(e){const t=JSON.parse(localStorage.getItem("authState"));t&&Object.assign(e,t)}}},o=(0,r.MT)({modules:{auth:n}});o.commit("auth/getState");var u=o,d=s(2483),c=s(2268);const m=(0,l._)("h2",{class:"text-h2 mb-4"},"Login",-1);function h(e,t,s,a,i,r){const n=(0,l.up)("v-alert"),o=(0,l.up)("v-text-field"),u=(0,l.up)("v-btn"),d=(0,l.up)("v-form"),h=(0,l.up)("v-card"),g=(0,l.up)("v-col"),p=(0,l.up)("v-row"),f=(0,l.up)("v-container");return(0,l.wg)(),(0,l.j4)(f,{class:"fill-height"},{default:(0,l.w5)((()=>[(0,l.Wm)(p,{align:"center",justify:"center"},{default:(0,l.w5)((()=>[(0,l.Wm)(g,{cols:"12",sm:"8",md:"6"},{default:(0,l.w5)((()=>[(0,l.Wm)(h,{class:"elevation-12 pa-8"},{default:(0,l.w5)((()=>[i.loginSuccess?((0,l.wg)(),(0,l.j4)(n,{key:0,type:"success",icon:"mdi-check-circle-outline",class:"mb-4"},{default:(0,l.w5)((()=>[(0,l.Uk)("Login erfolgreich!")])),_:1})):(0,l.kq)("",!0),i.loginFailed?((0,l.wg)(),(0,l.j4)(n,{key:1,type:"error",icon:"mdi-alert-circle-outline",class:"mb-4"},{default:(0,l.w5)((()=>[(0,l.Uk)("Login fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),m,(0,l.Wm)(d,{ref:"form"},{default:(0,l.w5)((()=>[(0,l.Wm)(o,{type:"text",id:"username",modelValue:i.formData.username,"onUpdate:modelValue":t[0]||(t[0]=e=>i.formData.username=e),rules:[i.rules.required],label:"Benutzername"},null,8,["modelValue","rules"]),(0,l.Wm)(o,{type:"password",id:"password",modelValue:i.formData.password,"onUpdate:modelValue":t[1]||(t[1]=e=>i.formData.password=e),rules:[i.rules.required],label:"Passwort"},null,8,["modelValue","rules"]),(0,l.Wm)(u,{color:"#2196F3",dark:"",block:"",onClick:r.validateAndSubmitForm},{default:(0,l.w5)((()=>[(0,l.Uk)("Anmelden")])),_:1},8,["onClick"]),i.validationError?((0,l.wg)(),(0,l.j4)(n,{key:0,type:"error",class:"mt-4"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,c.zw)(i.validationError),1)])),_:1})):(0,l.kq)("",!0)])),_:1},512)])),_:1})])),_:1})])),_:1})])),_:1})}const g="https://guesto.azurewebsites.net/user/login";var p={data(){return{formData:{username:"",password:""},loginSuccess:!1,loginFailed:!1,validationError:"",rules:{required:e=>!!e||"Dieses Feld ist erforderlich"}}},methods:{async validateAndSubmitForm(){this.loginSuccess=!1,this.loginFailed=!1,this.validationError="",this.$refs.form.validate()?this.submitForm():this.validationError="Bitte füllen Sie alle erforderlichen Felder aus."},async submitForm(){if(this.loginSuccess=!1,this.loginFailed=!1,this.validationError="",this.formData.username&&this.formData.password)try{const e=await this.$axios.post(g,this.formData);this.loginSuccess=!0,200===e.status&&(u.state.auth.loggedIn=!0,u.state.auth.username=e.data.username,u.state.auth.role=e.data.role,u.state.auth.id=e.data.id,u.commit("auth/setJWT",e.data.token),setTimeout((()=>{this.$router.push("/events")}),1e3))}catch(e){this.loginFailed=!0,console.error("Fehler bei der Anmeldung:",e)}else this.validationError="Bitte füllen Sie alle erforderlichen Felder aus."}},created(){},name:"LoginForm"},f=s(89);const w=(0,f.Z)(p,[["render",h]]);var v=w;const k={key:0},y=(0,l._)("div",{style:{clear:"both"}},null,-1);function b(e,t,s,i,r,n){const o=(0,l.up)("v-alert"),u=(0,l.up)("v-icon"),d=(0,l.up)("v-data-table"),m=(0,l.up)("v-btn"),h=(0,l.up)("v-container"),g=(0,l.up)("v-text-field"),p=(0,l.up)("guest-view"),f=(0,l.up)("assign-view"),w=(0,l.up)("v-form");return(0,l.wg)(),(0,l.iD)(l.HY,null,[r.success&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:0,type:"success"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern erfolgreich!")])),_:1})):(0,l.kq)("",!0),r.failed&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:1,type:"error"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),r.deleteSuccess&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:2,type:"success"},{default:(0,l.w5)((()=>[(0,l.Uk)("Löschen erfolgreich!")])),_:1})):(0,l.kq)("",!0),r.deleteFailed&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:3,type:"error"},{default:(0,l.w5)((()=>[(0,l.Uk)("Löschen fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),r.selectedEvent?(0,l.kq)("",!0):((0,l.wg)(),(0,l.j4)(h,{key:4},{default:(0,l.w5)((()=>[(0,l.Wm)(d,{items:r.events,headers:r.headers,"items-per-page":r.itemsPerPage,"server-items-length":r.totalEvents,"item-key":"eventName",loading:r.loading,"onUpdate:page":n.onPageChange},{item:(0,l.w5)((({item:e})=>[(0,l._)("tr",null,[(0,l._)("td",null,(0,c.zw)(e.eventName),1),(0,l._)("td",null,(0,c.zw)(e.eventTimeDisplay),1),(0,l._)("td",null,(0,c.zw)(e.location),1),(0,l._)("td",null,(0,c.zw)(e.checkedInGuestsCount),1),(0,l._)("td",null,(0,c.zw)(e.totalGuestCount),1),(0,l._)("td",null,[(0,l.Wm)(u,{size:"small",class:"me-2",onClick:t=>n.editEvent(e),color:"#2196F3"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-pencil ")])),_:2},1032,["onClick"]),(0,l.Wm)(u,{size:"small",onClick:t=>n.deleteItem(e),color:"rgb(200, 35, 51)"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-delete ")])),_:2},1032,["onClick"])])])])),_:1},8,["items","headers","items-per-page","server-items-length","loading","onUpdate:page"]),(0,l.Wm)(m,{class:"text-none mb-4 create-btn",color:"#2196F3",onClick:t[0]||(t[0]=e=>n.createEvent())},{default:(0,l.w5)((()=>[(0,l.Uk)("Erstellen")])),_:1})])),_:1})),r.selectedEvent?((0,l.wg)(),(0,l.j4)(h,{key:5},{default:(0,l.w5)((()=>[(0,l.Wm)(w,{ref:"form",onSubmit:(0,a.iM)(n.submitForm,["prevent"])},{default:(0,l.w5)((()=>[(0,l.Wm)(g,{type:"text",id:"eventName",modelValue:r.formData.eventName,"onUpdate:modelValue":t[1]||(t[1]=e=>r.formData.eventName=e),rules:[e=>!!e||"Bitte Event Name eingeben"],required:"",label:"Event Name"},null,8,["modelValue","rules"]),(0,l.Wm)(g,{type:"datetime-local",id:"eventTime",modelValue:r.formData.eventTime,"onUpdate:modelValue":t[2]||(t[2]=e=>r.formData.eventTime=e),rules:[e=>!!e||"Bitte Event Zeit eingeben"],required:"",label:"Event Zeit"},null,8,["modelValue","rules"]),(0,l.Wm)(g,{type:"number",id:"maxGuestList",modelValue:r.formData.maxGuestList,"onUpdate:modelValue":t[3]||(t[3]=e=>r.formData.maxGuestList=e),rules:[e=>!!e||"Bitte Max Anzahl Gäste eingeben"],required:"",label:"Max Anzahl Gäste"},null,8,["modelValue","rules"]),(0,l.Wm)(g,{type:"number",id:"price",modelValue:r.formData.price,"onUpdate:modelValue":t[4]||(t[4]=e=>r.formData.price=e),rules:[e=>!!e||"Bitte Preis eingeben"],required:"",label:"Preis"},null,8,["modelValue","rules"]),(0,l.Wm)(g,{type:"text",id:"location",modelValue:r.formData.location,"onUpdate:modelValue":t[5]||(t[5]=e=>r.formData.location=e),rules:[e=>!!e||"Bitte Location eingeben"],required:"",label:"Location"},null,8,["modelValue","rules"]),r.formData.id?((0,l.wg)(),(0,l.iD)("div",k,[(0,l.Wm)(p,{eventId:r.formData.id,editAllowed:r.createdByMyUser},null,8,["eventId","editAllowed"]),n.isAdmin()?((0,l.wg)(),(0,l.j4)(f,{key:0,eventId:r.formData.id,editAllowed:r.createdByMyUser,assignedUserIds:r.assignedUserIds},null,8,["eventId","editAllowed","assignedUserIds"])):(0,l.kq)("",!0)])):(0,l.kq)("",!0),y,(0,l._)("div",null,[(0,l.Wm)(m,{type:"submit",class:"text-none mb-4 right-btn",color:"#2196F3"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern")])),_:1}),(0,l.Wm)(m,{class:"text-none mb-4 left-btn",color:"#757575",onClick:t[6]||(t[6]=e=>n.cancelForm())},{default:(0,l.w5)((()=>[(0,l.Uk)("Abbrechen")])),_:1})])])),_:1},8,["onSubmit"])])),_:1})):(0,l.kq)("",!0)],64)}function D(){let e=u.state.auth.access_token;return e?{Authorization:"Bearer "+e}:{}}function _(e,t,s,i,r,n){const o=(0,l.up)("v-alert"),u=(0,l.up)("v-col"),d=(0,l.up)("v-row"),m=(0,l.up)("v-expansion-panel-title"),h=(0,l.up)("v-icon"),g=(0,l.up)("v-data-table"),p=(0,l.up)("v-btn"),f=(0,l.up)("v-card-title"),w=(0,l.up)("v-text-field"),v=(0,l.up)("v-form"),k=(0,l.up)("v-card-text"),y=(0,l.up)("v-card-actions"),b=(0,l.up)("v-card"),D=(0,l.up)("v-dialog"),_=(0,l.up)("v-container"),U=(0,l.up)("v-expansion-panel-text"),A=(0,l.up)("v-expansion-panel"),S=(0,l.up)("v-expansion-panels");return(0,l.wg)(),(0,l.iD)(l.HY,null,[r.success&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:0,type:"success"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern erfolgreich!")])),_:1})):(0,l.kq)("",!0),r.failed&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:1,type:"error"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),r.deleteSuccess&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:2,type:"success"},{default:(0,l.w5)((()=>[(0,l.Uk)("Löschen erfolgreich!")])),_:1})):(0,l.kq)("",!0),r.deleteFailed&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:3,type:"error"},{default:(0,l.w5)((()=>[(0,l.Uk)("Löschen fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),(0,l.Wm)(S,{style:{"margin-bottom":"20px"}},{default:(0,l.w5)((()=>[(0,l.Wm)(A,null,{default:(0,l.w5)((()=>[(0,l.Wm)(m,null,{default:(0,l.w5)((()=>[(0,l.Wm)(d,null,{default:(0,l.w5)((()=>[(0,l.Wm)(u,{cols:"4",class:"d-flex justify-start"},{default:(0,l.w5)((()=>[(0,l.Uk)(" Gäste ")])),_:1})])),_:1})])),_:1}),(0,l.Wm)(U,null,{default:(0,l.w5)((()=>[(0,l.Wm)(_,null,{default:(0,l.w5)((()=>[(0,l.Wm)(g,{items:r.guests,headers:r.headers,"items-per-page":r.itemsPerPage,"server-items-length":r.totalGuests,"item-key":"firstName",loading:r.loading,"onUpdate:page":n.onPageChange},{item:(0,l.w5)((({item:e})=>[(0,l._)("tr",null,[(0,l._)("td",null,(0,c.zw)(e.firstName+" "+e.lastName),1),(0,l._)("td",null,(0,c.zw)(e.additionalGuests),1),(0,l._)("td",null,[e.remainingCheckIns>0?((0,l.wg)(),(0,l.j4)(h,{key:0,size:"small",class:"me-2",onClick:t=>n.checkInGuest(e),color:"green"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-checkbox-marked ")])),_:2},1032,["onClick"])):(0,l.kq)("",!0),0==e.remainingCheckIns?((0,l.wg)(),(0,l.j4)(h,{key:1,size:"small",class:"me-2",onClick:t=>n.checkOutGuest(e),color:"rgb(200, 35, 51)"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-arrow-right-box ")])),_:2},1032,["onClick"])):(0,l.kq)("",!0),(0,l.Uk)(" "+(0,c.zw)(e.remainingCheckIns),1)]),(0,l._)("td",null,(0,c.zw)(n.getCheckedInDisplayText(e.checkedIn)),1),(0,l._)("td",null,(0,c.zw)(n.geCustomPriceDisplayText(e.customPrice)),1),(0,l._)("td",null,(0,c.zw)(e.comment),1),(0,l._)("td",null,(0,c.zw)(e.addedByDisplayText),1),(0,l._)("td",null,[(0,l.Wm)(h,{size:"small",class:"me-2",onClick:t=>n.editGuest(e),color:"#2196F3"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-pencil ")])),_:2},1032,["onClick"]),(0,l.Wm)(h,{size:"small",onClick:t=>n.deleteGuest(e),color:"rgb(200, 35, 51)"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-delete ")])),_:2},1032,["onClick"])])])])),_:1},8,["items","headers","items-per-page","server-items-length","loading","onUpdate:page"]),(0,l.Wm)(p,{class:"text-none mb-4 right-btn",color:"#2196F3",onClick:t[0]||(t[0]=e=>n.addGuest())},{default:(0,l.w5)((()=>[(0,l.Uk)("Gast hinzufügen")])),_:1}),(0,l.Wm)(D,{modelValue:r.guestDialogVisible,"onUpdate:modelValue":t[8]||(t[8]=e=>r.guestDialogVisible=e),"max-width":"500"},{default:(0,l.w5)((()=>[(0,l.Wm)(b,null,{default:(0,l.w5)((()=>[(0,l.Wm)(f,null,{default:(0,l.w5)((()=>[(0,l.Uk)((0,c.zw)(r.guestData.id?"Gast bearbeiten":"Gast hinzufügen"),1)])),_:1}),(0,l.Wm)(k,null,{default:(0,l.w5)((()=>[(0,l.Wm)(v,{onSubmit:(0,a.iM)(n.saveGuest,["prevent"]),ref:"guestForm"},{default:(0,l.w5)((()=>[(0,l.Wm)(w,{type:"text",id:"firstName",modelValue:r.guestData.firstName,"onUpdate:modelValue":t[1]||(t[1]=e=>r.guestData.firstName=e),rules:[e=>!!e||"Bitte Vorname eingeben"],required:"",label:"Vorname"},null,8,["modelValue","rules"]),(0,l.Wm)(w,{type:"text",id:"lastName",modelValue:r.guestData.lastName,"onUpdate:modelValue":t[2]||(t[2]=e=>r.guestData.lastName=e),rules:[e=>!!e||"Bitte Nachname eingeben"],required:"",label:"Nachname"},null,8,["modelValue","rules"]),(0,l.Wm)(w,{type:"number",id:"additionalGuests",modelValue:r.guestData.additionalGuests,"onUpdate:modelValue":t[3]||(t[3]=e=>r.guestData.additionalGuests=e),rules:[e=>0===e||!!e&&e>0||"Bitte eine gültige Anzahl an Begleitpersonen eingeben"],required:"",label:"Anzahl Begleitung"},null,8,["modelValue","rules"]),(0,l.Wm)(w,{type:"text",id:"comment",modelValue:r.guestData.comment,"onUpdate:modelValue":t[4]||(t[4]=e=>r.guestData.comment=e),rules:[e=>!!e||"Bitte Kommentar eingeben"],required:"",label:"Kommentar"},null,8,["modelValue","rules"]),(0,l.Wm)(w,{type:"number",id:"customPrice",modelValue:r.guestData.customPrice,"onUpdate:modelValue":t[5]||(t[5]=e=>r.guestData.customPrice=e),rules:[e=>0===e||!!e&&e>0||"Bitte einen gültigen Preis eingeben"],required:"",label:"Benutzerdefinierter Preis"},null,8,["modelValue","rules"])])),_:1},8,["onSubmit"])])),_:1}),(0,l.Wm)(y,null,{default:(0,l.w5)((()=>[(0,l.Wm)(p,{class:"text-none mb-4",color:"#757575",onClick:t[6]||(t[6]=e=>n.closeDialog())},{default:(0,l.w5)((()=>[(0,l.Uk)("Abbrechen")])),_:1}),(0,l.Wm)(p,{type:"submit",class:"text-none mb-4",color:"#2196F3",onClick:t[7]||(t[7]=e=>n.saveGuest())},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern")])),_:1})])),_:1})])),_:1})])),_:1},8,["modelValue"])])),_:1})])),_:1})])),_:1})])),_:1})],64)}const U="https://guesto.azurewebsites.net/event",A="https://guesto.azurewebsites.net/user/list";var S={props:{eventId:{type:Number,required:!0},editAllowed:{type:Boolean,required:!0}},data(){return{guests:[],headers:[{title:"Name",value:"firstNamelastName"},{title:"Anzahl Begleitung",value:"additionalGuests"},{title:"Übrige Check-ins",key:"remainingCheckIns"},{title:"Eingecheckt",key:"checkedIn"},{title:"B.def. Preis",key:"customPrice"},{title:"Kommentar",key:"comment"},{title:"Hinzugefügt von",key:"addedByDisplayText"},{title:"Aktionen",key:"actions"}],itemsPerPage:5,totalGuests:0,loading:!1,success:!1,failed:!1,deleteSuccess:!1,deleteFailed:!1,showAlert:!1,guestDialogVisible:!1,guestData:{firstName:"",lastName:"",additionalGuests:0,comment:"",customPrice:0},users:[]}},name:"GuestView",components:{},methods:{fetchData(){this.loading=!0,this.$axios.get(U+"/"+this.eventId+"/guest",{params:{_page:this.$route.query.page||1,_limit:this.itemsPerPage},headers:D()}).then((e=>{this.guests=e.data,this.guests.forEach((e=>e.addedByDisplayText=this.users.find((t=>t.id===e.addedBy)).username)),this.totalGuests=Number(e.headers["x-total-count"]),this.guests.actions="",this.loading=!1})).catch((e=>{console.error("Error fetching data:",e),this.loading=!1}))},fetchUsers(){this.loading=!0,this.$axios.get(A,{params:{},headers:D()}).then((e=>{this.users=e.data,this.loading=!1})).catch((e=>{console.error("Error fetching data:",e),this.loading=!1}))},onPageChange(e){this.$router.push({query:{page:e}}),this.fetchData()},async deleteGuest(e){const t=window.confirm("Sind Sie sicher, dass Sie diesen Gast löschen möchten?");if(t){this.resetAlert();try{let t=await this.$axios.delete(U+"/"+this.eventId+"/guest/"+e.id,{params:{},headers:D()});t&&(this.deleteSuccess=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3),this.fetchData())}catch(s){this.deleteFailed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)}}},addGuest(){this.openDialog()},editGuest(e){this.guestDialogVisible=!0,this.guestData={...this.guests.find((t=>t.id===e.id))}},getCheckedInDisplayText(e){return!1===e?"Nein":"Ja"},geCustomPriceDisplayText(e){return e+"€"},openDialog(){this.guestDialogVisible=!0},closeDialog(){this.guestDialogVisible=!1,this.guestData={firstName:"",lastName:"",additionalGuests:0,comment:"",customPrice:0}},async saveGuest(){this.resetAlert();const e=await this.$refs.guestForm.validate();if(e.valid)try{let e;e=this.guestData.id?await this.$axios.put(U+"/"+this.eventId+"/guest/"+this.guestData.id,this.guestData,{params:{},headers:D()}):await this.$axios.post(U+"/"+this.eventId+"/guest",this.guestData,{params:{},headers:D()}),e&&(this.success=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3),this.closeDialog(),this.fetchData())}catch(t){this.failed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)}},async checkOutGuest(e){const t=window.confirm("Sind Sie sicher, dass Sie diesen Gast auschecken möchten?");t&&await this.checkInGuest(e)},async checkInGuest(e){this.resetAlert();try{let t=await this.$axios.put(U+"/"+this.eventId+"/check-in/"+e.id,{},{params:{},headers:D()});t&&(this.success=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3),this.fetchData())}catch(t){this.failed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)}},resetAlert(){this.success=!1,this.failed=!1,this.deleteSuccess=!1,this.deleteFailed=!1}},watch:{"$route.query.page"(){this.fetchData()}},created(){this.fetchUsers(),this.fetchData()}};const x=(0,f.Z)(S,[["render",_]]);var W=x;function V(e,t,s,a,i,r){const n=(0,l.up)("v-alert"),o=(0,l.up)("v-col"),u=(0,l.up)("v-row"),d=(0,l.up)("v-expansion-panel-title"),c=(0,l.up)("v-select"),m=(0,l.up)("v-btn"),h=(0,l.up)("v-container"),g=(0,l.up)("v-expansion-panel-text"),p=(0,l.up)("v-expansion-panel"),f=(0,l.up)("v-expansion-panels");return(0,l.wg)(),(0,l.iD)(l.HY,null,[i.success&&e.showAlert?((0,l.wg)(),(0,l.j4)(n,{key:0,type:"success"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern erfolgreich!")])),_:1})):(0,l.kq)("",!0),i.failed&&e.showAlert?((0,l.wg)(),(0,l.j4)(n,{key:1,type:"error"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),(0,l.Wm)(f,{style:{"margin-bottom":"20px"}},{default:(0,l.w5)((()=>[(0,l.Wm)(p,null,{default:(0,l.w5)((()=>[(0,l.Wm)(d,null,{default:(0,l.w5)((()=>[(0,l.Wm)(u,null,{default:(0,l.w5)((()=>[(0,l.Wm)(o,{cols:"4",class:"d-flex justify-start"},{default:(0,l.w5)((()=>[(0,l.Uk)(" Zugeordnete Benutzer ")])),_:1})])),_:1})])),_:1}),(0,l.Wm)(g,null,{default:(0,l.w5)((()=>[(0,l.Wm)(h,null,{default:(0,l.w5)((()=>[(0,l.Wm)(c,{modelValue:i.assignedUsers,"onUpdate:modelValue":t[0]||(t[0]=e=>i.assignedUsers=e),items:i.allUsers,chips:"",label:"Benutzer",multiple:"","item-title":"displayText","item-value":"id"},null,8,["modelValue","items"]),(0,l._)("div",null,[(0,l.Wm)(m,{class:"text-none mb-4 right-btn",color:"#2196F3",onClick:t[1]||(t[1]=e=>r.saveAssignedUsers())},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern")])),_:1})])])),_:1})])),_:1})])),_:1})])),_:1})],64)}const I="https://guesto.azurewebsites.net";var q={props:{eventId:{type:Number,required:!0},editAllowed:{type:Boolean,required:!0},assignedUserIds:{type:Array,required:!0}},data(){return{assignedUsers:[],allUsers:[],loading:!1,success:!1,failed:!1}},name:"AssignView",components:{},methods:{fetchData(){this.loading=!0,this.$axios.get(I+"/user/list",{params:{},headers:D()}).then((e=>{this.allUsers=e.data,this.allUsers.forEach((e=>e.displayText=e.username+" ("+e.role+")")),this.assignedUsers=this.allUsers.filter((e=>this.assignedUserIds.includes(e.id))),this.loading=!1})).catch((e=>{console.error("Error fetching data:",e),this.loading=!1}))},async saveAssignedUsers(){console.log(this.assignedUsers),this.success=!1,this.failed=!1;let e=!1;for(let s=0;s<this.assignedUsers.length;s++)if(this.assignedUsers[s]){this.data={userId:this.assignedUsers[s]};try{await this.$axios.post(I+"/event/"+this.eventId+"/assign",this.data,{params:{},headers:D()})}catch(t){console.log(t),e=this.assignedUsers[s]}}e?(this.failed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)):(this.success=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3))}},watch:{"$route.query.page"(){this.fetchData()}},created(){this.fetchData()}};const F=(0,f.Z)(q,[["render",V]]);var T=F;const z="https://guesto.azurewebsites.net/event";var C={data(){return{events:[],headers:[{title:"Event Name",value:"eventName"},{title:"Event Time",value:"eventTime"},{title:"Location",value:"location"},{title:"Anzahl eingecheckte Gäste",value:"checkedInGuestsCount"},{title:"Anzahl Gäste",value:"totalGuestCount"},{title:"Aktionen",key:"actions"}],itemsPerPage:5,totalEvents:0,loading:!1,selectedEvent:!1,formData:{eventName:"",eventTime:null,maxGuestList:0,price:0,location:""},success:!1,failed:!1,deleteSuccess:!1,deleteFailed:!1,showAlert:!1,guestDialogVisible:!1,guestData:{firstName:"",lastName:"",additionalGuests:0,comment:"",customPrice:0},createdByMyUser:!1,myId:null,myRole:null,assignedUserIds:[]}},name:"EventsPage",components:{AssignView:T,GuestView:W},methods:{fetchData(){this.loading=!0,this.$axios.get(z,{params:{_page:this.$route.query.page||1,_limit:this.itemsPerPage,sortBy:"eventName",order:"asc"},headers:D()}).then((e=>{this.events=e.data,this.totalEvents=Number(e.headers["x-total-count"]),this.events.forEach((e=>{e.eventTimeDisplay=this.formatDate(e.eventTime);const t=new Date(e.eventTime),s=new Date(t.getTime()-6e4*t.getTimezoneOffset());e.eventTime=s.toISOString().slice(0,16)})),this.events.actions="",this.loading=!1})).catch((e=>{console.error("Error fetching data:",e),this.loading=!1}))},formatDate(e){if(!e)return"";const t={day:"2-digit",month:"2-digit",year:"numeric",hour:"2-digit",minute:"2-digit"};let s=new Date(e).toLocaleDateString("de-DE",t);return s=s.replace(/[,]/g," "),s},onPageChange(e){this.$router.push({query:{page:e}}),this.fetchData()},createEvent(){this.selectedEvent=!0},editEvent(e){this.selectedEvent=!0;let t=this.events.find((t=>t.id===e.id));this.formData={...t},this.createdByMyUser=t.createdBy===this.myId,t.assignedUserIds&&(this.assignedUserIds=t.assignedUserIds)},async deleteItem(e){const t=window.confirm("Sind Sie sicher, dass Sie dieses Event löschen möchten?");if(t){this.resetAlert();try{let t=await this.$axios.delete(z+"/"+e.id,{params:{},headers:D()});t&&(this.deleteSuccess=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3),this.fetchData())}catch(s){this.deleteFailed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)}}},cancelForm(){this.selectedEvent=!1,this.formData={eventName:"",eventTime:new Date,maxGuestList:0,price:0,location:""},this.assignedUserIds=[]},async submitForm(){this.resetAlert();const e=await this.$refs.form.validate();if(e.valid)try{let e;e=this.formData.id?await this.$axios.put(z+"/"+this.formData.id,this.formData,{params:{},headers:D()}):await this.$axios.post(z,this.formData,{params:{},headers:D()}),e&&(this.success=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3),this.cancelForm(),this.fetchData())}catch(t){this.failed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)}},isAdmin(){return"ADMIN"===this.myRole},resetAlert(){this.success=!1,this.failed=!1,this.deleteSuccess=!1,this.deleteFailed=!1}},watch:{"$route.query.page"(){this.fetchData()}},created(){console.log(u.state.auth),this.myId=u.state.auth.id,this.myRole=u.state.auth.role,this.fetchData()}};const P=(0,f.Z)(C,[["render",b]]);var E=P;function G(e,t,s,i,r,n){const o=(0,l.up)("v-alert"),u=(0,l.up)("v-icon"),d=(0,l.up)("v-data-table"),m=(0,l.up)("v-btn"),h=(0,l.up)("v-container"),g=(0,l.up)("v-text-field"),p=(0,l.up)("v-select"),f=(0,l.up)("v-form");return(0,l.wg)(),(0,l.iD)(l.HY,null,[r.success&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:0,type:"success"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern erfolgreich!")])),_:1})):(0,l.kq)("",!0),r.failed&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:1,type:"error"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),r.deleteSuccess&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:2,type:"success"},{default:(0,l.w5)((()=>[(0,l.Uk)("Löschen erfolgreich!")])),_:1})):(0,l.kq)("",!0),r.deleteFailed&&r.showAlert?((0,l.wg)(),(0,l.j4)(o,{key:3,type:"error"},{default:(0,l.w5)((()=>[(0,l.Uk)("Löschen fehlgeschlagen!")])),_:1})):(0,l.kq)("",!0),r.selectedUser?(0,l.kq)("",!0):((0,l.wg)(),(0,l.j4)(h,{key:4},{default:(0,l.w5)((()=>[(0,l.Wm)(d,{items:r.users,headers:r.headers,"items-per-page":r.itemsPerPage,"server-items-length":r.totalEvents,"item-key":"username",loading:r.loading,"onUpdate:page":n.onPageChange},{item:(0,l.w5)((({item:e})=>[(0,l._)("tr",null,[(0,l._)("td",null,(0,c.zw)(e.username),1),(0,l._)("td",null,(0,c.zw)(n.getRoleDisplayText(e.role)),1),(0,l._)("td",null,[(0,l.Wm)(u,{size:"small",class:"me-2",onClick:t=>n.editUser(e),color:"#2196F3"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-pencil ")])),_:2},1032,["onClick"]),(0,l.Wm)(u,{size:"small",onClick:t=>n.deleteUser(e),color:"rgb(200, 35, 51)"},{default:(0,l.w5)((()=>[(0,l.Uk)(" mdi-delete ")])),_:2},1032,["onClick"])])])])),_:1},8,["items","headers","items-per-page","server-items-length","loading","onUpdate:page"]),(0,l.Wm)(m,{class:"text-none mb-4 create-btn",color:"#2196F3",onClick:t[0]||(t[0]=e=>n.createUser())},{default:(0,l.w5)((()=>[(0,l.Uk)("Erstellen")])),_:1})])),_:1})),r.selectedUser?((0,l.wg)(),(0,l.j4)(h,{key:5},{default:(0,l.w5)((()=>[(0,l.Wm)(f,{ref:"form",onSubmit:(0,a.iM)(n.submitForm,["prevent"])},{default:(0,l.w5)((()=>[(0,l.Wm)(g,{type:"text",id:"username",modelValue:r.formData.username,"onUpdate:modelValue":t[1]||(t[1]=e=>r.formData.username=e),rules:[e=>!!e||"Benutzername ist erforderlich"],required:"",label:"Benutzername"},null,8,["modelValue","rules"]),r.formData.id?(0,l.kq)("",!0):((0,l.wg)(),(0,l.j4)(g,{key:0,type:"password",id:"password",modelValue:r.formData.password,"onUpdate:modelValue":t[2]||(t[2]=e=>r.formData.password=e),rules:[e=>!!e||"Passwort ist erforderlich"],required:"",label:"Passwort"},null,8,["modelValue","rules"])),(0,l.Wm)(p,{modelValue:r.formData.role,"onUpdate:modelValue":t[3]||(t[3]=e=>r.formData.role=e),label:"Rolle",items:r.roleOptions,"item-title":"label","item-value":"value"},null,8,["modelValue","items"]),(0,l._)("div",null,[(0,l.Wm)(m,{type:"submit",class:"text-none mb-4 right-btn",color:"#2196F3"},{default:(0,l.w5)((()=>[(0,l.Uk)("Speichern")])),_:1}),(0,l.Wm)(m,{class:"text-none mb-4 left-btn",color:"#757575",onClick:t[4]||(t[4]=e=>n.cancelForm())},{default:(0,l.w5)((()=>[(0,l.Uk)("Abbrechen")])),_:1})])])),_:1},8,["onSubmit"])])),_:1})):(0,l.kq)("",!0)],64)}const N="https://guesto.azurewebsites.net/user";var j={data(){return{users:[],headers:[{title:"Benutzername",value:"username"},{title:"Rolle",value:"role"},{title:"Aktionen",key:"actions"}],itemsPerPage:5,totalEvents:0,loading:!1,selectedUser:!1,formData:{username:"",password:"",role:"ADMIN"},roleOptions:[],success:!1,failed:!1,deleteSuccess:!1,deleteFailed:!1,showAlert:!1}},name:"UserPage",components:{},methods:{fetchData(){this.loading=!0,this.$axios.get(N+"/list",{params:{_page:this.$route.query.page||1,_limit:this.itemsPerPage},headers:D()}).then((e=>{this.users=e.data,this.totalEvents=Number(e.headers["x-total-count"]),this.users.actions="",this.loading=!1})).catch((e=>{console.error("Error fetching data:",e),this.loading=!1}))},onPageChange(e){this.$router.push({query:{page:e}}),this.fetchData()},createUser(){this.selectedUser=!0},editUser(e){this.selectedUser=!0,this.formData={...this.users.find((t=>t.id===e.id))}},getRoleDisplayText(e){return this.roleOptions.find((t=>t.value===e)).label},async deleteUser(e){const t=window.confirm("Sind Sie sicher, dass Sie diesen User löschen möchten?");if(t){this.deleteSuccess=!1,this.deleteFailed=!1;try{let t=await this.$axios.delete(N+"/"+e.id,{params:{},headers:D()});t&&(this.deleteSuccess=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3),this.fetchData())}catch(s){this.deleteFailed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)}}},cancelForm(){this.selectedUser=!1,this.formData={username:"",password:"",role:"ADMIN"}},async submitForm(){this.success=!1,this.failed=!1;const e=await this.$refs.form.validate();if(e.valid)try{let e;e=this.formData.id?await this.$axios.put(N+"/"+this.formData.id,this.formData,{params:{},headers:D()}):await this.$axios.post(N+"/register",this.formData,{params:{},headers:D()}),e&&(this.success=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3),this.cancelForm(),this.fetchData())}catch(t){this.failed=!0,this.showAlert=!0,setTimeout((()=>{this.showAlert=!1}),2e3)}}},watch:{"$route.query.page"(){this.fetchData()}},created(){this.roleOptions=[{value:"ADMIN",label:"Admin"},{value:"STAFF",label:"Mitarbeiter"},{value:"CONTROLLER",label:"Kontrolleur"}],this.fetchData()}};const B=(0,f.Z)(j,[["render",G]]);var $=B;const O=[{path:"/",redirect:"/login"},{path:"/login",component:v},{path:"/events",component:E},{path:"/users",component:$}],L=(0,d.p7)({history:(0,d.r5)(),routes:O});var M=L,R={__name:"App",setup(e){let t=(0,l.Fl)((()=>u.state.auth.loggedIn));function s(){u.commit("auth/logout"),M.push("/login")}return(e,a)=>{const r=(0,l.up)("router-link"),n=(0,l.up)("v-col"),o=(0,l.up)("v-row"),u=(0,l.up)("v-container"),d=(0,l.up)("v-app-bar"),c=(0,l.up)("router-view"),m=(0,l.up)("v-main"),h=(0,l.up)("v-app");return(0,l.wg)(),(0,l.j4)(h,null,{default:(0,l.w5)((()=>[(0,l.Wm)(d,{app:"",color:"#2196F3",dark:""},{default:(0,l.w5)((()=>[(0,l.Wm)(u,null,{default:(0,l.w5)((()=>[(0,l.Wm)(o,{align:"center",justify:"space-between"},{default:(0,l.w5)((()=>[(0,l.Wm)(n,null,{default:(0,l.w5)((()=>[(0,i.SU)(t)?(0,l.kq)("",!0):((0,l.wg)(),(0,l.j4)(r,{key:0,to:"/login",class:"nav-link"},{default:(0,l.w5)((()=>[(0,l.Uk)("Login")])),_:1})),(0,i.SU)(t)?((0,l.wg)(),(0,l.j4)(r,{key:1,to:"/events",class:"nav-link"},{default:(0,l.w5)((()=>[(0,l.Uk)("Events")])),_:1})):(0,l.kq)("",!0),(0,i.SU)(t)?((0,l.wg)(),(0,l.j4)(r,{key:2,to:"/users",class:"nav-link"},{default:(0,l.w5)((()=>[(0,l.Uk)("Benutzer")])),_:1})):(0,l.kq)("",!0),(0,i.SU)(t)?((0,l.wg)(),(0,l.iD)("button",{key:3,onClick:s,class:"button button--primary button_navigation nav-link right-btn"},"Logout")):(0,l.kq)("",!0)])),_:1})])),_:1})])),_:1})])),_:1}),(0,l.Wm)(m,null,{default:(0,l.w5)((()=>[(0,l.Wm)(u,null,{default:(0,l.w5)((()=>[(0,l.Wm)(c)])),_:1})])),_:1})])),_:1})}}};const Z=R;var H=Z,J=s(1076),K=(s(9773),s(8957)),Y=s(4926),Q=s(8600),X=s(596),ee=s(3504);const te=(0,K.Rd)({icons:{defaultSet:"mdi",aliases:ee.j,sets:{mdi:ee.t,fa:X.fa}},components:Y,directives:Q}),se=(0,a.ri)(H);se.use(te),se.use(M),se.use(u),se.config.globalProperties.$axios=J.Z,se.mount("#app")}},t={};function s(a){var l=t[a];if(void 0!==l)return l.exports;var i=t[a]={exports:{}};return e[a].call(i.exports,i,i.exports,s),i.exports}s.m=e,function(){var e=[];s.O=function(t,a,l,i){if(!a){var r=1/0;for(d=0;d<e.length;d++){a=e[d][0],l=e[d][1],i=e[d][2];for(var n=!0,o=0;o<a.length;o++)(!1&i||r>=i)&&Object.keys(s.O).every((function(e){return s.O[e](a[o])}))?a.splice(o--,1):(n=!1,i<r&&(r=i));if(n){e.splice(d--,1);var u=l();void 0!==u&&(t=u)}}return t}i=i||0;for(var d=e.length;d>0&&e[d-1][2]>i;d--)e[d]=e[d-1];e[d]=[a,l,i]}}(),function(){s.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return s.d(t,{a:t}),t}}(),function(){s.d=function(e,t){for(var a in t)s.o(t,a)&&!s.o(e,a)&&Object.defineProperty(e,a,{enumerable:!0,get:t[a]})}}(),function(){s.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"===typeof window)return window}}()}(),function(){s.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)}}(),function(){s.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})}}(),function(){var e={143:0};s.O.j=function(t){return 0===e[t]};var t=function(t,a){var l,i,r=a[0],n=a[1],o=a[2],u=0;if(r.some((function(t){return 0!==e[t]}))){for(l in n)s.o(n,l)&&(s.m[l]=n[l]);if(o)var d=o(s)}for(t&&t(a);u<r.length;u++)i=r[u],s.o(e,i)&&e[i]&&e[i][0](),e[i]=0;return s.O(d)},a=self["webpackChunkgusto_app"]=self["webpackChunkgusto_app"]||[];a.forEach(t.bind(null,0)),a.push=t.bind(null,a.push.bind(a))}();var a=s.O(void 0,[998],(function(){return s(7512)}));a=s.O(a)})();
//# sourceMappingURL=app.ee276932.js.map