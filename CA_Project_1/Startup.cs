using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using APIGateway.DB;
using APIGateway.Extensions;
using APIGateway.Services;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.DataProtection;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

namespace APIGateway
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllersWithViews();
            //services.AddSession();
            //inject the service
            services.AddScoped<HttpClient>();
            services.AddScoped<DataFetcher>();
            services.AddScoped<DBUser>();
            //inject dbcontext
            services.AddDbContext<UserContext>(opt =>
               opt.UseLazyLoadingProxies()
               .UseSqlServer(Configuration.GetConnectionString("DbConn")));

        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env, UserContext dbcontext)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();

            app.UseAuthorization();

            //app.UseSession();
            //use extenstions to register middleware
            app.UseMiddlewareExtensions();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllerRoute(
                    name: "default",
                    pattern: "{controller=Gallery}/{action=Index}/{id?}");
            });

            //create database and table
            //dbcontext.Database.EnsureDeleted();
            //dbcontext.Database.EnsureCreated();

            ////insert data into database
            //new DBSeeder(dbcontext);

        }
    }
}
