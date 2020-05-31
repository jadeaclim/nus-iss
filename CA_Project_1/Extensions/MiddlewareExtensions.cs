using APIGateway.Middlewares;
using Microsoft.AspNetCore.Builder;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIGateway.Extensions
{
    public static class MiddlewareExtensions
    {
        public static IApplicationBuilder UseMiddlewareExtensions(this IApplicationBuilder app)
        {
            app.UseMiddleware<Middleware>();
            return app;
        }
    }
}
