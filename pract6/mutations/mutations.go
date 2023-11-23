package mutations

import "github.com/graphql-go/graphql"

func GetRootFields() graphql.Fields {
	return graphql.Fields{
		"create": GetCreateProductMutation(),
		"update": GetUpdateProductMutation(),
		"delete": GetDeleteProductMutation(),
	}
}
