using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OTP_SP.Models
{
    public class UserCreation
    {
        public string UserName { get; set; }
        public string Password { get; set; }
        public string OTP { get; set; }
    }

    public class OTPValidation
    {
        public string usernameService { get; set; }
        public string serviceName { get; set; }
        public string otp { get; set; }
    }
}