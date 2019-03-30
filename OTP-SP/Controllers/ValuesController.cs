using System;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace OTP_SP.Controllers
{
    public class ValuesController : ApiController
    {
        // GET api/values
        public HttpResponseMessage Get(HttpRequestMessage json)
        {
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // GET api/values/5
        //public string Get(int id)
        //{
        //    return "value";
        //}

        // POST api/values
        [Route("api/values/RegisterNewUserName")]
        [HttpPost]
        public HttpResponseMessage Post(HttpRequestMessage value)
        {
            var user = Newtonsoft.Json.JsonConvert.DeserializeObject<Models.UserCreation>(value.Content.ReadAsStringAsync().Result);
            Helpers.SQLHelper.RecordUser(user);
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        [Route("api/values/ValidateCreds")]
        [HttpPost]
        public HttpResponseMessage ValidateCreds(HttpRequestMessage json)
        {
            try
            {
                var user = Newtonsoft.Json.JsonConvert.DeserializeObject<Models.UserCreation>(json.Content.ReadAsStringAsync().Result);
                if (Helpers.SQLHelper.CheckUserCreds(user))
                {
                    using (var client = new HttpClient())
                    {
                        client.BaseAddress = new System.Uri("http://10.38.162.55:3010/");//otp/check-otp
                        client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
                        var message = new Models.OTPValidation()
                        {
                            usernameService = user.UserName,
                            serviceName = "sfr",
                            otp = user.OTP
                        };

                        var response = client.PostAsJsonAsync("otp/check-otp", message).Result;

                        if (response.Content.ReadAsAsync<bool>().Result)
                            return Request.CreateResponse(HttpStatusCode.OK, "Login succesful");
                        else
                            return Request.CreateResponse(HttpStatusCode.Unauthorized, "Invalid OTP.");
                    }
                }
                else
                    return Request.CreateResponse(HttpStatusCode.Unauthorized, "UserName or Password false.");
            }
            catch (Exception ex)
            {
                return Request.CreateResponse(HttpStatusCode.OK, ex);
            }
        }

        // PUT api/values/5
        //public void Put(int id, [FromBody]string value)
        //{
        //}

        // DELETE api/values/5
        //public void Delete(int id)
        //{
        //}
    }
}
