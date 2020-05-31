using APIGateway.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIGateway.DB
{
    public class UserContext : DbContext
    {
        protected IConfiguration configuration;

        public UserContext(DbContextOptions<UserContext> options)
            : base(options)
        {
        }
        protected override void OnModelCreating(ModelBuilder model)
        {

        }

        public DbSet<Models.User> User { set; get; }

    }
}