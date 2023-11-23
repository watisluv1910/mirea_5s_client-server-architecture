package main

import (
	"log"
	"net/http"

	"github.com/graphql-go/graphql"
	"github.com/graphql-go/handler"
	"pract6/mutations"
	"pract6/queries"
)

func main() {

	var schemaConfig = graphql.SchemaConfig{
		Query: graphql.NewObject(graphql.ObjectConfig{
			Name:   "query",
			Fields: queries.GetRootFields(),
		}),
		Mutation: graphql.NewObject(graphql.ObjectConfig{
			Name:   "mutation",
			Fields: mutations.GetRootFields(),
		}),
	}

	var schema, err = graphql.NewSchema(schemaConfig)

	if err != nil {
		log.Fatalf("Failed to create new schema, error: %v", err)
	}

	httpHandler := handler.New(&handler.Config{
		Schema: &schema,
		Pretty: true,
	})

	http.Handle("/product", httpHandler)

	http.ListenAndServe(":8080", nil)
}
