# create index with mapping
curl -XPUT 'http://localhost:9200/siroop' -d @mapping.json

# bulk insert data
curl -XPOST http://localhost:9200/_bulk --data-binary "@bulkinsert.json"