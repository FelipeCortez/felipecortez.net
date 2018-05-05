from os import listdir, makedirs, chdir, curdir
from os.path import isfile, join, abspath
from jinja2 import Template, Markup, Environment, FileSystemLoader, select_autoescape
import markdown
import shutil
import errno
import http.server
import socketserver
import re

env = Environment(loader=FileSystemLoader(searchpath='./templates/'),
                  autoescape=select_autoescape(['html', 'xml']))

STATIC_DIR = "static"
TEMPLATES_DIR = "templates"
HTML_INPUT_DIR = "input"
POSTS_DIR = "posts"
OUTPUT_DIR = "output"
BLOG_OUTPUT_DIR = "blog"
PORT = 8000

posts = []

def copy_anything(src, dst):
    try:
        shutil.copytree(src, dst)
    except OSError as exc:
        if exc.errno == errno.ENOTDIR:
            shutil.copy(src, dst)
        else: raise

def get_date_from_filename(filename):
    return re.match(r"(\d+)-(\d+)-(\d+)", filename).groups()

def get_files_in_folder(folder):
    files = [f for f in listdir(folder) if isfile(join(folder, f))]
    return [join(folder, f) for f in files]

def gen_posts():
    makedirs(join(OUTPUT_DIR, BLOG_OUTPUT_DIR))
    post_paths = get_files_in_folder(POSTS_DIR)
    for path in post_paths:
        post = {}
        y, m, d = get_date_from_filename(path[6:])
        post["date"] = "-".join([y, m, d])
        with open(path, 'r') as f:
            post["special"] = f.readline().strip()
            if "ignore" not in post["special"]:
                post["slug"] = f.readline().strip()
                f.readline()
                post["title"] = f.readline()
                post["contents"] = f.read()
                post["contents"] = Markup(markdown.markdown(post["contents"], extensions=["markdown.extensions.tables"]))
                posts.append(post)
                #print("Special", post["special"])
                #print("Title", post["title"])
                #print("Date", post["date"])
                #print("Contents", post["contents"])

    post_template = env.get_template("post.html")
    for post in posts:
        print(post["title"])
        html = post_template.render(title="Felipe Cortez - {}".format(post["title"]), post=post)

        with open(join(OUTPUT_DIR, BLOG_OUTPUT_DIR, post["slug"] + ".html"), 'w') as f:
            f.write(html)

def copy_static():
    """Copy the contents of static to the output folder"""
    static_contents = [f for f in listdir(STATIC_DIR)]
    print(static_contents)
    for thing in static_contents:
        copy_anything(join(STATIC_DIR, thing), join(OUTPUT_DIR, thing))

def gen_index():
    base_template = env.get_template("base.html")
    html = base_template.render(title="Felipe Cortez")

    with open(join(OUTPUT_DIR, "index.html"), 'w') as index_file:
        index_file.write(html)

    base_template = env.get_template("camilify.html")
    html = base_template.render(title="camilify")

    with open(join(OUTPUT_DIR, "camilify.html"), 'w') as camilifile:
        camilifile.write(html)

    #base_template = env.get_template("nomes.html")
    #html = base_template.render(title="nomes")

    with open(join(OUTPUT_DIR, "nomes.html"), 'w') as camilifile:
        camilifile.write(html)

def gen_blog_index():
    blog_index_template = env.get_template("blog.html")
    html = blog_index_template.render(title="Felipe Cortez", posts=posts, blog_output_dir = BLOG_OUTPUT_DIR)
    with open(join(OUTPUT_DIR, BLOG_OUTPUT_DIR, "index.html"), 'w') as index_file:
        index_file.write(html)

def gen_blog():
    #with open(join(TEMPLATES_DIR, "base.html"), 'r') as base_template_file:
    #    base_template = base_template_file.read()
    #    template = Template(base_template).render(title="Felipe Cortez")
    #    with open(join(OUTPUT_DIR, "post.html"), 'w') as index_file:
    #        index_file.write(template)

    gen_posts()
    gen_blog_index()

def serve_output():
    chdir(join(abspath(curdir), OUTPUT_DIR))
    Handler = http.server.SimpleHTTPRequestHandler

    with socketserver.TCPServer(("", PORT), Handler) as httpd:
        print("serving at port", PORT)
        httpd.serve_forever()

def gen_output():
    try:
        shutil.rmtree(OUTPUT_DIR)
    except:
        pass

    try:
        makedirs(OUTPUT_DIR)
    except OSError as e:
        if e.errno != errno.EEXIST:
            raise

    copy_static()
    gen_index()
    gen_blog()
    print("All done.")

gen_output()
