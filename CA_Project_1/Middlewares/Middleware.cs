using APIGateway.Models;
using APIGateway.Utils;
using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

namespace APIGateway.Middlewares
{
    public class Middleware
    {
        private readonly RequestDelegate next;

        public Middleware(RequestDelegate next)
        {
            this.next = next;
        }


        public async Task Invoke(HttpContext context)
        {
            string controller = (string)context.Request.RouteValues["controller"];
            string action = (string)context.Request.RouteValues["action"];
            //get sessionId from cookie
            if (controller == "Login")
            {
                if( action == "Index")
                {
                    string token = context.Request.Cookies["token"];
                    //judge the token
                    if (token != null)
                    {
                        User user = JsonConvert.DeserializeObject<User>(RSA.RSADecrypt(token).ToString());
                        if (user != null)
                        {
                            context.Response.Redirect("https://" +
                               context.Request.Host + "/Gallery");
                            return;
                        }
                    }
                }
            }
            else if(controller != "Gallery")
            {
                string token = context.Request.Cookies["token"];
                //judge the token
                if (token == null)
                {
                    context.Response.Redirect("https://" +
                        context.Request.Host + "/Login/Index");
                    return;
                }
                User user=JsonConvert.DeserializeObject<User>(RSA.RSADecrypt(token).ToString());
                if (user == null)
                {
                    context.Response.Redirect("https://" +
                       context.Request.Host + "/Login/Index");
                    return;
                }
                //judge the userId is correct
            }


            await next(context);
        }
    }
}
