local command = "/usr/local/GraphicsMagick/bin/gm convert " .. ngx.var.request_filepath .. " -resize " .. ngx.var.width .. "x" .. ngx.var.height .. " +profile \"*\" " .. ngx.var.request_filepath .. "_" .. ngx.var.width .. "x" .. ngx.var.height .. ".jpg";
os.execute(command);
ngx.exec(ngx.var.request_uri);