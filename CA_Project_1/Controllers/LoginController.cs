using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using APIGateway.Models;
using Microsoft.Data.SqlClient;
using APIGateway.DB;
using APIGateway.Services;
using Microsoft.AspNetCore.Http;
using APIGateway.Utils;
using Newtonsoft.Json;

namespace APIGateway.Controllers
{
    public class LoginController : Controller
    {
        protected DBUser db;

        public LoginController(DBUser db)

        {
            this.db = db;
        }

        public IActionResult Index()
        {
            return View();
        }

        public string Verify([FromBody]User user)
        {
            User userDetails = db.retrieveUserByNamePwd(user);
            if (userDetails == null)
            {
                userDetails=new User() { ErrMsg = "Wrong Username or Password entered." };
                return System.Text.Json.JsonSerializer.Serialize(userDetails);
            }
            user.Password = null;
            user.Id = userDetails.Id;
            User userToken = new User() { Id = userDetails.Id,Username=userDetails.Username };
            string token = RSA.RSAEncryption(user); //get token
            CookieOptions options = new CookieOptions();
            options.Expires = DateTime.Now.AddDays(1); //set cookie expiry
            Response.Cookies.Append("token", token, options);
            return System.Text.Json.JsonSerializer.Serialize(new Operand() { Value= "https://" +
                        Request.Host + "/Gallery/Index"
            });
        }

        [Route("/Logout")]
        public bool Logout()
        {
            Response.Cookies.Append("token","",new CookieOptions() { Expires=DateTime.Now.AddDays(-1)});
            return true;
        }

    }
}



